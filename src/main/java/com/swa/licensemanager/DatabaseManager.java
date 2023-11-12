package com.swa.licensemanager;

import util.ColumnDefinition;

import java.sql.*;
import java.util.HashMap;

import static com.swa.licensemanager.DatabaseMapping.DATABASEMAPPING;
import static connect_db.PostgreSQL.establishDBConnection;

public class DatabaseManager {


    public static final Connection CONNECTION = establishDBConnection();


    private DatabaseManager() {

    }

    private static void checkDatabaseValidity() {
        String extension = "CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\";";
        try {
            Statement statement = CONNECTION.createStatement();
            statement.execute(extension);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getMaxLength(Connection connection, String tableName, String columnName) throws SQLException {
        String query = "SELECT character_maximum_length " +
                "FROM information_schema.columns " +
                "WHERE table_name = ? AND column_name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, columnName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("character_maximum_length");
                }
            }
        }

        // Return a default value or handle the case where no result is found
        return -1;
    }

    /**
     * Inserts a payload into a given Table.
     *
     * @param table         It uses the enum {@link  DatabaseMapping.TABLENAMES}
     * @param insertPayload A Map of String,String Value Pairs reflecting column name and field value
     * @return True if the Insert was successfully made, false if there was an error
     */
    public static boolean insertIntoTable(DatabaseMapping.TABLENAMES table, HashMap<String, String> insertPayload) {
        //Checking for null Values and returning false if Insert not possible
        if (table == null || table.getTableName() == null || table.getTableName().isEmpty()) {
            return false;
        }

        String tableName = table.getTableName();
        HashMap<String, ColumnDefinition> mapping = DATABASEMAPPING.get(tableName);
        if (mapping == null) return false;
        if (!insertPayload.keySet().containsAll(mapping.keySet())) {
            StringBuilder errorStringBuilder = new StringBuilder();
            errorStringBuilder.append("Error inserting into Table ").append(tableName).append("\n");
            // Handle missing fields
            mapping.forEach((k, v) -> {
                if (!insertPayload.containsKey(k)) {
                    errorStringBuilder.append(" field ").append(k).append(" with required Value ").append(v).append(" is missing \n");
                }
            });
            System.err.println("Missing fields in updatePayload.");
            System.err.println(errorStringBuilder);
            return false;
        }
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (HashMap.Entry<String, String> entry : insertPayload.entrySet()) {
            columns
                    .append(entry.getKey())
                    .append(",");
            values.append("?").append(",");
        }

        columns.deleteCharAt(columns.length() - 1);
        values.deleteCharAt(values.length() - 1);

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(query)) {
            int parameterIndex = 1;
            for (String value : insertPayload.values()) {
                preparedStatement.setString(parameterIndex++, value);
            }
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateTableEntry(DatabaseMapping.TABLENAMES table, HashMap<String, String> search, HashMap<String, String> updatePayload) {
        String tableName = table.getTableName();

        if (tableName == null) {
            // Handle invalid table name
            return false;
        }

        StringBuilder setClause = new StringBuilder();
        StringBuilder whereClause = new StringBuilder();

        for (HashMap.Entry<String, String> entry : updatePayload.entrySet()) {
            setClause.append(entry.getKey()).append(" = ?,");
        }

        setClause.deleteCharAt(setClause.length() - 1);

        for (HashMap.Entry<String, String> entry : search.entrySet()) {
            whereClause.append(entry.getKey()).append(" = ? AND ");
        }

        whereClause.delete(whereClause.length() - 5, whereClause.length()); // Remove the last "AND"

        String query = String.format("UPDATE %s SET %s WHERE %s", tableName, setClause, whereClause);

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(query)) {
            int parameterIndex = 1;
            // Set values for SET clause
            for (String value : updatePayload.values()) {
                preparedStatement.setString(parameterIndex++, value);
            }
            // Set values for WHERE clause
            for (String value : search.values()) {
                preparedStatement.setString(parameterIndex++, value);
            }
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    static void init() {
        checkDatabaseValidity();
        validateTables();
    }

    private static void validateTables() {
        for (DatabaseMapping.TABLENAMES s : DatabaseMapping.TABLENAMES.values()) {
            boolean tableValid;
            tableValid = checkTable(s.getTableName());
            tableValid = tableValid && checkDataValid(s.getTableName());

            if (!tableValid) {
                System.out.println("Something went wrong checking Table " + s.getTableName());
            } else {
                System.out.println("Success checking table " + s.getTableName());
            }
        }
    }

    private static boolean checkTable(String tableName) {
        System.out.println("Checking Table " + tableName);
        if (!doesTableExist(tableName)) {
            if (createTable(tableName)) {
                System.out.println("Created Table " + tableName);
                return true;
            }
            return false;
        } else {
            System.out.println("Table " + tableName + " exists ");
            return true;
        }
    }


    private static boolean checkDataValid(String tableName) {
        //TODO implement
        return true;
    }

    private static boolean doesTableExist(String tableName) {
        DatabaseMetaData metadata;
        try {
            metadata = DatabaseManager.CONNECTION.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (ResultSet resultSet = metadata.getTables(null, null, tableName, null)) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean createTable(String tableName) {
        try {

            if (DATABASEMAPPING.get(tableName) == null) {
                return false;
            }
            HashMap<String, ColumnDefinition> mapping = DATABASEMAPPING.get(tableName);
            if(mapping == null)return false;
            // Build the CREATE TABLE query using the fieldMap
            String createTableQuery = buildCreateTableQuery(tableName, mapping);
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(createTableQuery);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildCreateTableQuery(String tableName, HashMap<String, ColumnDefinition> fieldMap) {
        StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        createTableQuery.append(tableName).append(" (");

        // Build the column part of the query
        StringBuilder columnPart = new StringBuilder();
        for (HashMap.Entry<String, ColumnDefinition> entry : fieldMap.entrySet()) {
            columnPart
                    .append(entry.getKey())
                    .append(" ")
                    .append(entry.getValue().resolveColumnDefinition())
                    .append(",");
        }
        columnPart.deleteCharAt(columnPart.length() - 1); // Remove the last comma

        // Complete the query
        createTableQuery.append(columnPart).append(")");
        System.out.println(createTableQuery);
        return createTableQuery.toString();
    }
}
