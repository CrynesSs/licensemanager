package com.swa.properSpring.services;

import com.swa.properSpring.user.Employee;
import com.swa.properSpring.user.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {


    private final PasswordEncoder passwordEncoder;


    private final EmployeeRepository entityRepository;

    public EmployeeService(PasswordEncoder passwordEncoder, EmployeeRepository entityRepository) {
        this.passwordEncoder = passwordEncoder;
        this.entityRepository = entityRepository;
    }

    public void saveOrUpdateEntity(Employee.Builder builder) {
        // Encode the password before saving or updating
        String encodedPassword = passwordEncoder.encode(builder.getPassword());
        builder.setPassword(encodedPassword);

        // Save or update the entity in the repository
        entityRepository.save(builder.build());
    }
}
