package com.swa.connect_db;

import java.sql.DriverManager;
import java.sql.Connection;

public class PostgreSQL {
    public static Connection establishDBConnection() {
        Connection connection=null;

        try{
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection("jdbc:postgresql://localhost:3306/postgres", "postgres","nNw3E2Z8n4JqMeSm");

            if (connection!=null)
                System.out.println("DB connected");
            else System.out.println("DB connection failed");

        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

}
