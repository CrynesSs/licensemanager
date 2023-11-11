package com.swa.licensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LicensemanagerApplication {
	public static void main(String[] args) {
		DatabaseManager.init();
		SpringApplication.run(LicensemanagerApplication.class, args);
	}

}
