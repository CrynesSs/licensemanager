package com.swa.properSpring.customer;

import com.swa.properSpring.contract.Contract;
import com.swa.properSpring.models.EmployeeModel;
import com.swa.properSpring.user.Employee;
import com.swa.security.AccessRoles;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Secured({AccessRoles.USER})
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @GetMapping("/{customerName}")
    @Secured({AccessRoles.USER})
    public Customer getCustomerByName(@PathVariable String customerName) {
        return repository.findAll().stream().filter((customer -> Objects.equals(customer.getCustomerName(), customerName))).findFirst().orElseGet(Customer::new);
    }

    @GetMapping("/employees")
    @Secured({AccessRoles.USER})
    public Map<String, List<Employee>> getCustomerEmployees() {
        List<Customer> customers = repository.findAll();
        return customers.stream().collect(Collectors.toMap(Customer::getCustomerName, Customer::getUsers));
    }

    @GetMapping("/contracts")
    @Secured({AccessRoles.USER})
    public Map<String, List<Contract>> getCustomerContracts() {
        List<Customer> customers = repository.findAll();
        return customers.stream().collect(Collectors.toMap(Customer::getCustomerName, Customer::getContracts));
    }

    @PostMapping(value = "/employees", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Secured({AccessRoles.ADMIN})
    ResponseEntity<Employee> createClient(@ModelAttribute EmployeeModel model) {
        System.out.println(model);
        return ResponseEntity.ok().build();
    }
}
