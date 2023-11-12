package com.swa;

import com.swa.unnütz.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LicensemanagerApplication {
	private static final Logger log = LoggerFactory.getLogger(LicensemanagerApplication.class);
	public static void main(String[] args) {
		DatabaseManager.init();
		SpringApplication.run(LicensemanagerApplication.class, args);
	}
}
