package com.swa.licensemanager;

import com.github.javafaker.Faker;
import com.swa.properSpring.customer.Customer;
import com.swa.properSpring.customer.CustomerRepository;
import com.swa.properSpring.user.Employee;
import com.swa.properSpring.user.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EmployeeService service;

    private final CustomerRepository repository;

    @Autowired
    public DatabaseLoader(EmployeeService service, CustomerRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        Faker faker = new Faker();
        for(int i=0;i<10;++i){
            Customer.Builder customer1 = new Customer
                    .Builder()
                    .setCustomerName(faker.company().name())
                    .setAddressDetail(faker.address().fullAddress())
                    .setAddressDetail(faker.address().fullAddress());
            Customer c = customer1.build();
            for(int k=0;k<10;++k){
                Employee.Builder employeeBuilder = new Employee
                        .Builder(faker.name().username(), faker.internet().password())
                        .addCompany(c.getCustomerName())
                        .addName(faker.name().firstName(), faker.name().lastName())
                        .addEmail(faker.internet().emailAddress())
                        .addPhone(faker.phoneNumber().phoneNumber()).addPhone(faker.phoneNumber().phoneNumber())
                        .addRole("USER");
                if(Math.random() > 0.5){
                    employeeBuilder.addRole("ADMIN");
                }
                service.saveOrUpdateEntity(employeeBuilder.build());
                c.getUsers().add(employeeBuilder.build());
            }
            repository.save(c);
        }
        Employee.Builder employeeBuilder = new Employee
                .Builder("BilboBaggins1337", "kekwbadpassword")
                .addCompany(faker.company().name())
                .addName(faker.name().firstName(), faker.name().lastName())
                .addEmail(faker.internet().emailAddress())
                .addPhone(faker.phoneNumber().phoneNumber()).addPhone(faker.phoneNumber().phoneNumber())
                .addRole("USER").addRole("ADMIN");
        service.saveOrUpdateEntity(employeeBuilder.build());
    }
}
