package com.swa.licensemanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;


@SpringBootApplication
public class LicensemanagerApplication {
	public static void main(String[] args) {
		DatabaseManager.init();
		SpringApplication.run(LicensemanagerApplication.class, args);
	}
}
