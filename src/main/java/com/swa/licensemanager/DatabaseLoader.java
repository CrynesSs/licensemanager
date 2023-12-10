package com.swa.licensemanager;

import com.github.javafaker.Faker;
import com.swa.properSpring.customer.Customer;
import com.swa.properSpring.customer.CustomerRepository;
import com.swa.properSpring.user.Employee;
import com.swa.properSpring.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EmployeeService service;

    private final Faker faker = new Faker();
    private final CustomerRepository repository;

    @Autowired
    public DatabaseLoader(EmployeeService service, CustomerRepository repository) {
        this.service = service;
        this.repository = repository;
    }


    private void createData(){
        for(int i=0;i<10;++i){
            Customer.Builder customer1 = new Customer
                    .Builder()
                    .setCustomerName(faker.company().name())
                    .setAddressDetail(faker.address().fullAddress())
                    .setAddressDetail(faker.address().fullAddress());
            Customer c = customer1.build();
            repository.save(c);
            for(int k=0;k<10;++k){
                Employee.Builder employeeBuilder = new Employee
                        .Builder(faker.name().username(), faker.internet().password())
                        .addCompany(c)
                        .addName(faker.name().firstName(), faker.name().lastName())
                        .addEmail(faker.internet().emailAddress())
                        .addPhone(faker.phoneNumber().phoneNumber()).addPhone(faker.phoneNumber().phoneNumber())
                        .addRole("USER");
                if(Math.random() > 0.5){
                    employeeBuilder.addRole("ADMIN");
                }
                try{
                    service.saveOrUpdateEntity(employeeBuilder);
                }catch (Exception e){
                    System.out.println(employeeBuilder);
                }

                c.getUsers().add(employeeBuilder.build());
            }
        }
    }
    @Override
    public void run(String... args) {
        createData();
        Customer.Builder customer1 = new Customer
                .Builder()
                .setCustomerName(faker.company().name())
                .setAddressDetail(faker.address().fullAddress())
                .setAddressDetail(faker.address().fullAddress());
        Customer s = customer1.build();
        repository.save(s);
        Employee.Builder employeeBuilder = new Employee
                .Builder("BilboBaggins1337", "kekwbadpassword")
                .addCompany(s)
                .addName(faker.name().firstName(), faker.name().lastName())
                .addEmail(faker.internet().emailAddress())
                .addPhone(faker.phoneNumber().phoneNumber()).addPhone(faker.phoneNumber().phoneNumber())
                .addRole("USER").addRole("ADMIN");
        service.saveOrUpdateEntity(employeeBuilder);
    }
}
