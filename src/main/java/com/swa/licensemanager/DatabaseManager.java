package com.swa.licensemanager;

import java.sql.*;
import java.util.HashMap;

import static connect_db.PostgreSQL.establishDBConnection;

public class DatabaseManager {
    enum DATABASENAMES {
        USERS("users"),
        CUSTOMERS("customers"),
        CONTRACTS("contracts");

        public String getTableName() {
            return tableName;
        }

        private final String tableName;

        DATABASENAMES(String tableName) {
            this.tableName = tableName;
        }
    }

    public static final Connection CONNECTION = establishDBConnection();

    // Map<Tablename,Tablefields>
    private static final HashMap<String, HashMap<String, String>> DATABASEMAPPING = new HashMap<>();

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

    public static boolean insertIntoTable(DATABASENAMES table, HashMap<String, String> updatePayload) {
        if (table == null || table.getTableName() == null || table.getTableName().isEmpty()) {
            return false;
        }
        String tableName = table.getTableName();
        if (!updatePayload.keySet().containsAll(DATABASEMAPPING.get(tableName).keySet())) {
            // Handle missing fields
            System.err.println("Missing fields in updatePayload.");
            return false;
        }
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (HashMap.Entry<String, String> entry : updatePayload.entrySet()) {
            columns.append(entry.getKey()).append(",");
            values.append("?").append(",");
        }

        columns.deleteCharAt(columns.length() - 1);
        values.deleteCharAt(values.length() - 1);

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(query)) {
            int parameterIndex = 1;
            for (String value : updatePayload.values()) {
                preparedStatement.setString(parameterIndex++, value);
            }
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateTableEntry(DATABASENAMES table, HashMap<String, String> search, HashMap<String, String> updatePayload) {
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

    private static void setTablesNames() {
        DATABASEMAPPING.put(DATABASENAMES.USERS.getTableName(), DatabaseMapping.USERTABLEMAPPING);
        DATABASEMAPPING.put(DATABASENAMES.CUSTOMERS.getTableName(), DatabaseMapping.CUSTOMERTABLEMAPPING);
        DATABASEMAPPING.put(DATABASENAMES.CONTRACTS.getTableName(), DatabaseMapping.CONTRACTTABLEMAPPING);
    }

    static void init() {
        checkDatabaseValidity();
        setTablesNames();
        validateTables();
    }

    private static void validateTables() {
        for (DATABASENAMES s : DATABASENAMES.values()) {
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
            if (!DATABASEMAPPING.containsKey(tableName) || DATABASEMAPPING.get(tableName).isEmpty()) {
                return false;
            }
            // Build the CREATE TABLE query using the fieldMap
            String createTableQuery = buildCreateTableQuery(tableName, DATABASEMAPPING.get(tableName));
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(createTableQuery);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildCreateTableQuery(String tableName, HashMap<String, String> fieldMap) {
        StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        createTableQuery.append(tableName).append(" (");

        // Build the column part of the query
        StringBuilder columnPart = new StringBuilder();
        for (HashMap.Entry<String, String> entry : fieldMap.entrySet()) {
            columnPart.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
        }
        columnPart.deleteCharAt(columnPart.length() - 1); // Remove the last comma

        // Complete the query
        createTableQuery.append(columnPart).append(")");
        return createTableQuery.toString();
    }
}
