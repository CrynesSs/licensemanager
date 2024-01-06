package com.swa.properSpring.customer;

import com.swa.properSpring.contract.Contract;
import com.swa.properSpring.models.CustomerModel;
import com.swa.properSpring.models.EmployeeModel;
import com.swa.properSpring.services.CustomerService;
import com.swa.properSpring.services.EmployeeService;
import com.swa.properSpring.user.Employee;
import com.swa.security.AccessRoles;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerRepository repository;

    private final EmployeeService employeeService;

    CustomerController(CustomerRepository repository, EmployeeService employeeService) {
        this.repository = repository;
        this.employeeService = employeeService;
    }

    @GetMapping
    @Secured({AccessRoles.USER})
    public Set<CustomerModel> getAllCustomers() {
        return repository.findAll().stream().map(CustomerModel::fromCustomer).collect(Collectors.toSet());
    }

    //GetSingleCustomerByName
    @GetMapping("/{customerName}")
    @Secured({AccessRoles.USER})
    public Customer getCustomerByName(@PathVariable String customerName) {
        return repository.findByCustomerName(customerName);
    }

    @GetMapping("/companyNames")
    @Secured({AccessRoles.USER})
    public Set<String> getAllCompanyNames() {
        return repository.findAll().stream().map(Customer::getCustomerName).collect(Collectors.toSet());
    }

    @GetMapping("/employees")
    @Secured({AccessRoles.USER})
    public Map<String, Set<EmployeeModel>> getCustomerEmployees() {
        return repository.findAll().stream().collect(Collectors.toMap(Customer::getCustomerName, Customer::getEmployeesForAPI));
    }

    @GetMapping("/employees/{companyName}")
    @Secured({AccessRoles.USER})
    public Set<EmployeeModel> getSingleCustomerEmployees(@PathVariable String companyName) {
        Customer customer = repository.findByCustomerName(companyName);
        return customer == null ? new HashSet<>() : customer.getEmployees().stream().map(EmployeeModel::fromEmployee).collect(Collectors.toSet());
    }

    @GetMapping("/contracts")
    @Secured({AccessRoles.USER})
    public Map<String, Set<Contract>> getCustomerContracts() {
        return repository.findAll().stream().collect(Collectors.toMap(Customer::getCustomerName, Customer::getContracts));
    }

    @PostMapping(value = "/employees", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Secured({AccessRoles.ADMIN})
    ResponseEntity<?> createClient(@ModelAttribute EmployeeModel model) {
        boolean successful = employeeService.saveEntityWithCustomer(model.toEmployee(),model.getCompany());
        return successful ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
    @PutMapping
    @Secured({AccessRoles.ADMIN})
    ResponseEntity<?> createOrUpdateCustomer(@ModelAttribute EmployeeModel model){
        boolean successful = employeeService.saveEntityWithCustomer(model.toEmployee(),model.getCompany());
        return successful ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
