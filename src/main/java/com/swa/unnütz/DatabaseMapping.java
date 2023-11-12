package com.swa.unnütz;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;

public class DatabaseMapping {
    public static ImmutableMap<String, HashMap<String, ColumnDefinition>> DATABASEMAPPING;
    private static final HashMap<String, ColumnDefinition> USERTABLEMAPPING = new HashMap<>();
    private static final HashMap<String, ColumnDefinition> CUSTOMERTABLEMAPPING = new HashMap<>();
    private static final HashMap<String, ColumnDefinition> CONTRACTTABLEMAPPING = new HashMap<>();

    static {
        createUserTableMapping();
        createCustomerTableMapping();
        createContractTableMapping();


        setTables();

    }
    // Map<Tablename,Tablefields>

    public enum TABLENAMES {
        USERS("users"),
        CUSTOMERS("customers"),
        CONTRACTS("contracts");

        public String getTableName() {
            return tableName;
        }

        private final String tableName;

        TABLENAMES(String tableName) {
            this.tableName = tableName;
        }
    }

    private static void setTables() {
        DatabaseMapping.DATABASEMAPPING = new ImmutableMap.Builder<String,HashMap<String,ColumnDefinition>>()
                .put(TABLENAMES.USERS.getTableName(), DatabaseMapping.USERTABLEMAPPING)
                .put(TABLENAMES.CUSTOMERS.getTableName(), DatabaseMapping.CUSTOMERTABLEMAPPING)
                .put(TABLENAMES.CONTRACTS.getTableName(), DatabaseMapping.CONTRACTTABLEMAPPING)
                .build();
        //DATABASEMAPPING.put(TABLENAMES.USERS.getTableName(), DatabaseMapping.USERTABLEMAPPING);
        //DATABASEMAPPING.put(TABLENAMES.CUSTOMERS.getTableName(), DatabaseMapping.CUSTOMERTABLEMAPPING);
        //DATABASEMAPPING.put(TABLENAMES.CONTRACTS.getTableName(), DatabaseMapping.CONTRACTTABLEMAPPING);
    }


    private static void createContractTableMapping() {
        CONTRACTTABLEMAPPING.put("contract_id",new ColumnDefinition(ColumnDefinition.SQLDataType.SERIALKEY,SQLConstraint.SERIAL, SQLConstraint.PRIMARY_KEY));
        CONTRACTTABLEMAPPING.put("customer_id", new ColumnDefinition(ColumnDefinition.SQLDataType.UUID));
        CONTRACTTABLEMAPPING.put("start_date", new ColumnDefinition(ColumnDefinition.SQLDataType.DATE));
        CONTRACTTABLEMAPPING.put("end_date", new ColumnDefinition(ColumnDefinition.SQLDataType.DATE));
        CONTRACTTABLEMAPPING.put("version", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(50)));
        CONTRACTTABLEMAPPING.put("volume", new ColumnDefinition(ColumnDefinition.SQLDataType.INT));
        CONTRACTTABLEMAPPING.put("responsible_user", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255)));
    }

    private static void createCustomerTableMapping() {
        CUSTOMERTABLEMAPPING.put("customer_id", new ColumnDefinition(ColumnDefinition.SQLDataType.UUID,SQLConstraint.PRIMARY_KEY,SQLConstraint.DEFAULT));
        CUSTOMERTABLEMAPPING.put("name",new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.UNIQUE,SQLConstraint.NOT_NULL));
        CUSTOMERTABLEMAPPING.put("addressdetaila", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.UNIQUE,SQLConstraint.NOT_NULL));
        CUSTOMERTABLEMAPPING.put("addressdetailb", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.UNIQUE,SQLConstraint.NOT_NULL));
    }

    private static void createUserTableMapping() {
        USERTABLEMAPPING.put("id", new ColumnDefinition(ColumnDefinition.SQLDataType.SERIALKEY,SQLConstraint.SERIAL,SQLConstraint.PRIMARY_KEY));
        USERTABLEMAPPING.put("username", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.UNIQUE,SQLConstraint.NOT_NULL));
        USERTABLEMAPPING.put("password", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.NOT_NULL));
        USERTABLEMAPPING.put("email", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.UNIQUE,SQLConstraint.NOT_NULL));
        USERTABLEMAPPING.put("company", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.NOT_NULL));
        USERTABLEMAPPING.put("first_name", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.NOT_NULL));
        USERTABLEMAPPING.put("last_name", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.NOT_NULL));
        USERTABLEMAPPING.put("phone1", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.UNIQUE,SQLConstraint.NOT_NULL));
        USERTABLEMAPPING.put("phone2", new ColumnDefinition(ColumnDefinition.SQLDataType.VARCHAR,SQLConstraint.VARCHAR(255),SQLConstraint.NOT_NULL));
    }

}
