package com.swa.properSpring.user;

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

    public void saveOrUpdateEntity(Employee entity) {
        // Encode the password before saving or updating
        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);

        // Save or update the entity in the repository
        entityRepository.save(entity);
    }
}
