package com.swa.licensemanager;

import java.util.HashMap;

public class DatabaseMapping {
    public static final HashMap<String,String> USERTABLEMAPPING = new HashMap<>();
    public static final HashMap<String,String> CUSTOMERTABLEMAPPING = new HashMap<>();
    static {
        createUserTableMapping();
        createCustomerTableMapping();
    }
    private static void createCustomerTableMapping(){
        CUSTOMERTABLEMAPPING.put("id","SERIAL PRIMARY KEY");
        CUSTOMERTABLEMAPPING.put("name","VARCHAR(255) UNIQUE NOT NULL");
        CUSTOMERTABLEMAPPING.put("addressdetaila","VARCHAR(255) UNIQUE NOT NULL");
        CUSTOMERTABLEMAPPING.put("addressdetailb","VARCHAR(255) UNIQUE NOT NULL");

    }
    private static void createUserTableMapping(){
        USERTABLEMAPPING.put("id", "SERIAL PRIMARY KEY");
        USERTABLEMAPPING.put("username", "VARCHAR(50) UNIQUE NOT NULL");
        USERTABLEMAPPING.put("password", "VARCHAR(255) NOT NULL");
        USERTABLEMAPPING.put("email", "VARCHAR(100) UNIQUE NOT NULL");
        USERTABLEMAPPING.put("company", "VARCHAR(100)");
        USERTABLEMAPPING.put("first_name", "VARCHAR(50)");
        USERTABLEMAPPING.put("last_name", "VARCHAR(50)");
        USERTABLEMAPPING.put("phone1", "VARCHAR(20)");
        USERTABLEMAPPING.put("phone2", "VARCHAR(20)");
    }

}
