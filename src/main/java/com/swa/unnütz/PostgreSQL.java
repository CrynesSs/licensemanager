package com.swa.unnütz;

import java.sql.DriverManager;
import java.sql.Connection;

public class PostgreSQL {
    public static Connection establishDBConnection() {
        Connection connection=null;

        try{
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection("jdbc:postgresql://cornelius.db.elephantsql.com:5432/cuedmzfq", "cuedmzfq","tslvvypLIOYieo_34e_U1ma0--i894HC");

            if (connection!=null)
                System.out.println("DB connected");
            else System.out.println("DB connection failed");

        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

}
