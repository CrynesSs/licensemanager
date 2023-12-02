package com.swa.properSpring.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class EmployeeController {

    private final EmployeeRepository userRepository;

    public EmployeeController(EmployeeRepository repository) {
        this.userRepository = repository;
    }

    @GetMapping
    @Secured({"ROLE_USER"})
    public List<Employee> getUsers(Authentication authentication) {
        authentication.isAuthenticated();
        return userRepository.findAll();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<Employee> createClient(@RequestBody Employee client) {
        Employee savedClient = userRepository.save(client);
        try {
            return ResponseEntity.created(new URI("/clients/" + savedClient.getId())).body(savedClient);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateClient(@PathVariable Long id, @RequestBody Employee client) {
        Employee currentClient = userRepository.findById(id).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(currentClient);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteClient(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public Employee getClient(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
