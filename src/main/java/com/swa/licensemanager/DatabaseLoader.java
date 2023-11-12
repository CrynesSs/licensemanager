package com.swa.licensemanager;

import com.swa.properSpring.user.Employee;
import com.swa.properSpring.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final EmployeeRepository repository;
    @Autowired
    public DatabaseLoader(EmployeeRepository repository) {
        this.repository = repository;
    }
    @Override
    public void run(String... args) {
        this.repository.save(new Employee("Bilbo_Baggins69XXX", "burglar","frodo@middle-earth.fm","Hobbits.inc","Frodo","Baggins","13376969555","kek", "admin"));
    }
}
