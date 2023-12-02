package com.swa.properSpring.customer;

import com.swa.properSpring.contract.Contract;
import com.swa.properSpring.user.Employee;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository){
        this.repository = repository;
    }
    @GetMapping
    public List<Customer> getAllCustomers(){
        return repository.findAll();
    }
    @GetMapping("/employees")
    @Secured({"ROLE_USER"})
    public Map<String, List<Employee>> getCustomerEmployees(){
        List<Customer> customers = repository.findAll();
        return customers.stream().collect(Collectors.toMap(Customer::getCustomerName,Customer::getUsers));
    }
    @GetMapping("/contracts")
    public Map<String, List<Contract>> getCustomerContracts(){
        List<Customer> customers = repository.findAll();
        return customers.stream().collect(Collectors.toMap(Customer::getCustomerName,Customer::getContracts));
    }
}
