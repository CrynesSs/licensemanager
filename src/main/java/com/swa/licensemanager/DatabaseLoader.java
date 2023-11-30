package com.swa.licensemanager;

import com.swa.properSpring.user.Employee;
import com.swa.properSpring.user.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EmployeeService service;

    @Autowired
    public DatabaseLoader(EmployeeService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        Employee.Builder employee1 = new Employee
                .Builder("BilboBaggins1337", "kekwbadpassword")
                .addCompany("MiddleEarthInc.")
                .addName("Bilbo", "Baggins")
                .addEmail("bilbo@middle-earth.fm")
                .addPhone("13376258238").addPhone("1238741122384")
                .addRole("ADMIN").addRole("USER");
        service.saveOrUpdateEntity(employee1.build());

        service.saveOrUpdateEntity(new Employee("Frodo_Baggins69XXX", "burglar", "frodo@middle-earth.fm", "Hobbits.inc", "Frodo", "Baggins", "13376969555", "kek", new HashSet<>(Collections.singleton("ADMIN"))));
    }
}
