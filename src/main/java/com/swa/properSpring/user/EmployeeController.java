package com.swa.properSpring.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class EmployeeController {

    @Autowired
    private EmployeeRepository userRepository;

    @GetMapping
    public List<Employee> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public Employee addUser(@RequestBody Employee user) {
        return userRepository.save(user);
    }
}
