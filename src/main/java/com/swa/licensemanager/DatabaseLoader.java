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
            for(int k=0;k<10;++k){
                Employee.Builder employeeBuilder = new Employee
                        .Builder(faker.name().username(), faker.internet().password())
                        .addName(faker.name().firstName(), faker.name().lastName())
                        .addEmail(faker.internet().emailAddress())
                        .addPhone(faker.phoneNumber().phoneNumber()).addPhone(faker.phoneNumber().phoneNumber())
                        .addRole("USER");
                if(Math.random() > 0.5){
                    employeeBuilder.addRole("ADMIN");
                }
                try{
                    Employee e = employeeBuilder.build();
                    service.saveOrUpdateEntity(e);
                    c.addUser(e);
                    repository.save(c);
                }catch (Exception e){
                    System.out.println(employeeBuilder);
                }
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
                .addName(faker.name().firstName(), faker.name().lastName())
                .addEmail(faker.internet().emailAddress())
                .addPhone(faker.phoneNumber().phoneNumber()).addPhone(faker.phoneNumber().phoneNumber())
                .setCompany(s)
                .addRole("USER").addRole("ADMIN");
        Employee e =employeeBuilder.build();
        s.addUser(e);
        service.saveOrUpdateEntity(e);
        repository.save(s);
    }
}
