package com.swa.licensemanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

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
    private static final HashMap<String,HashMap<String,String>> DATABASEMAPPING = new HashMap<>();
    private static DatabaseManager INSTANCE = null;
    private DatabaseManager() {
        setTablesNames();
        init();
    }

    public static DatabaseManager getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new DatabaseManager();
        }
        return INSTANCE;
    }

    private void init() {
        ArrayList<DATABASENAMES> failedTables = (ArrayList<DATABASENAMES>) Arrays.stream(DATABASENAMES.values())
                .filter(databasename -> !checkTable(databasename.getTableName()))
                .collect(Collectors.toList());
        System.out.println(failedTables);
    }
    private void setTablesNames(){
        DATABASEMAPPING.put(DATABASENAMES.USERS.getTableName(),DatabaseMapping.USERTABLEMAPPING);
        DATABASEMAPPING.put(DATABASENAMES.CUSTOMERS.getTableName(),DatabaseMapping.CUSTOMERTABLEMAPPING);
    }
    private boolean checkTable(String tableName) {
        if (!doesTableExist(tableName)) {
            if (createTable(tableName)) {
                System.out.println("Created Table " + tableName);
                return true;
            }
            return false;
        } else {
            return checkDataValid(tableName);
        }
    }


    private boolean createTable(String tableName) {
        try {
            Statement statement = CONNECTION.createStatement();
            if(!DATABASEMAPPING.containsKey(tableName) || DATABASEMAPPING.get(tableName).isEmpty()){
                return false;
            }
            // Build the CREATE TABLE query using the fieldMap
            String createTableQuery = buildCreateTableQuery(tableName, DATABASEMAPPING.get(tableName));
            return statement.execute(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private static String buildCreateTableQuery(String tableName, HashMap<String, String> fieldMap) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");

        // Iterate through the fieldMap and append field definitions to the query
        for (HashMap.Entry<String, String> entry : fieldMap.entrySet()) {
            queryBuilder.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
        }
        // Remove the trailing comma and close the parentheses
        queryBuilder.deleteCharAt(queryBuilder.length() - 1); // Remove the last comma
        queryBuilder.append(")");
        System.out.println(queryBuilder);
        return queryBuilder.toString();
    }

    private boolean checkDataValid(String tableName) {
        //TODO implement
        return false;
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
}
