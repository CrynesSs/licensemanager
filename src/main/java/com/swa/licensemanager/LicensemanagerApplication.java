package com.swa.licensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.swa.licensemanager.DatabaseManager.getINSTANCE;


@SpringBootApplication
public class LicensemanagerApplication {

	public static final DatabaseManager DBMANAGER = getINSTANCE();

	public static void main(String[] args) {
		SpringApplication.run(LicensemanagerApplication.class, args);

	}

}
