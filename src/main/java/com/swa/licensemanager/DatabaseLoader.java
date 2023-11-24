package com.swa.licensemanager;

import com.swa.properSpring.user.Employee;
import com.swa.properSpring.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final EmployeeRepository repository;
    @Autowired
    public DatabaseLoader(EmployeeRepository repository) {
        this.repository = repository;
    }
    @Override
    public void run(String... args) {
        Employee.Builder employee1 = new Employee.Builder("BilboBaggins1337","kekwbadpassword");
        employee1.addCompany("MiddleEarthInc.");
        employee1.addName("Bilbo","Baggins");
        employee1.addEmail("bilbo@middle-earth.fm");
        employee1.addPhone("13376258238").addPhone("1238741122384");
        employee1.addRole("ADMIN");
        this.repository.save(employee1.build());

        this.repository.save(new Employee("Frodo_Baggins69XXX", "burglar","frodo@middle-earth.fm","Hobbits.inc","Frodo","Baggins","13376969555","kek", new HashSet<>(Collections.singleton("ADMIN"))));
    }
}
