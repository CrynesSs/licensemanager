package com.swa.unnütz;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

import static com.swa.unnütz.DatabaseManager.CONNECTION;
import static com.swa.unnütz.DatabaseMapping.DATABASEMAPPING;

public class DatabaseUtil {
    public static int getMaxVarcharLength(Connection connection, String tableName, String columnName) {
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
        }catch (SQLException e){
            e.printStackTrace();
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
    public static boolean insertIntoTable(DatabaseMapping.TABLENAMES table, HashMap<String, Object> insertPayload) {
        //Checking for null Values and returning false if Insert not possible
        if (table == null || table.getTableName() == null || table.getTableName().isEmpty()) {
            return false;
        }
        String tableName = table.getTableName();

        HashMap<String, ColumnDefinition> mapping = DATABASEMAPPING.get(tableName);
        //Check if a mapping exists for the table
        if (mapping == null) return false;
        //check for missing fields in the Payload
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
        //Datavalidation of the Payload
        boolean dataValid = true;
        for(HashMap.Entry<String, Object> entry : insertPayload.entrySet()){
            String columnName = entry.getKey();
            Object value = entry.getValue();

            ColumnDefinition columnDefinition = mapping.get(columnName);

            if (columnDefinition != null) {
                ColumnDefinition.SQLDataType expectedDataType = columnDefinition.dataType();
                if (!validateDataType(value, expectedDataType)) {
                    // Data type validation failed
                    dataValid = false;
                    break;
                }
            } else {
                // Handle the case where the column is not found in the mapping
                dataValid = false;
                break;
            }
        }
        if(!dataValid){
            System.out.println("Invalid Data in Insert");
        }
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (HashMap.Entry<String, Object> entry : insertPayload.entrySet()) {
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
            for (String value : insertPayload.values().stream().map(Object::toString).toList()) {
                preparedStatement.setString(parameterIndex++, value);
            }
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static boolean validateDataType(Object value, ColumnDefinition.SQLDataType expectedDataType) {
        return switch (expectedDataType) {
            case INT -> value instanceof Integer;
            case VARCHAR -> value instanceof String;
            case DATE -> value instanceof Date;
            case ENUM -> value instanceof Enum<?>;
            case UUID -> value instanceof UUID;
            case SERIALKEY -> false;
        };
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
}
