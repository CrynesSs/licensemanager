package com.swa.unnütz;

import java.sql.*;
import java.util.HashMap;

import static com.swa.unnütz.DatabaseMapping.DATABASEMAPPING;
import static com.swa.unnütz.PostgreSQL.establishDBConnection;

public class DatabaseManager {
    public static final Connection CONNECTION = establishDBConnection();

    private DatabaseManager() {

    }
    public static void init() {
        checkDatabaseValidity();
        validateTables();
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

    private static void validateTables() {
        for (DatabaseMapping.TABLENAMES s : DatabaseMapping.TABLENAMES.values()) {
            boolean tableValid = checkTable(s.getTableName());
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
            System.out.println("Table " + tableName + " exists");
            return true;
        }
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
