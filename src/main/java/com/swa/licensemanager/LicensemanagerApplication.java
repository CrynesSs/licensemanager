package com.swa.licensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Connection;
import connect_db.PostgreSQL;


@SpringBootApplication
public class LicensemanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicensemanagerApplication.class, args);
		Connection connection = PostgreSQL.establishDBConnection();
	}

}
