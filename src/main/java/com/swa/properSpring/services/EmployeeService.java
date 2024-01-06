package com.swa.properSpring.services;

import com.swa.properSpring.customer.Customer;
import com.swa.properSpring.customer.CustomerRepository;
import com.swa.properSpring.user.Employee;
import com.swa.properSpring.user.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {


    private final PasswordEncoder passwordEncoder;

    private final EmployeeRepository entityRepository;

    private final CustomerRepository customerRepository;

    public EmployeeService(PasswordEncoder passwordEncoder, EmployeeRepository entityRepository, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.entityRepository = entityRepository;
        this.customerRepository = customerRepository;
    }

    public boolean saveEntityWithCustomer(Employee employee,String customerName){
        Customer customer = customerRepository.findByCustomerName(customerName);
        if(customer == null) return false;
        employee.setCustomer(customer);
        customer.addUser(employee);
        customerRepository.save(customer);
        entityRepository.save(employee);
        return true;
    }
    public void saveOrUpdateEntity(Employee employee) {
        // Encode the password before saving or updating
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);

        // Save or update the entity in the repository
        entityRepository.save(employee);
    }
}
