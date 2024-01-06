package com.swa.properSpring.user;

import com.swa.properSpring.models.EmployeeModel;
import com.swa.properSpring.services.EmployeeService;
import com.swa.security.AccessRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class EmployeeController {

    private final EmployeeRepository userRepository;

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeRepository repository, EmployeeService employeeService) {
        this.userRepository = repository;
        this.employeeService = employeeService;
    }

    @GetMapping
    @Secured({AccessRoles.USER})
    public Set<Employee> getUsers(Authentication authentication) {
        System.out.println(authentication.getCredentials().toString());
        return authentication.isAuthenticated() ? new HashSet<>(userRepository.findAll()) : Collections.emptySet();
    }

    @Secured({AccessRoles.ADMIN})
    @PostMapping
    public ResponseEntity<?> createClient(@ModelAttribute EmployeeModel model) {
        boolean successful = employeeService.saveEntityWithCustomer(model.toEmployee(), model.getCompany());
        return successful ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /**
     * Updates the Client with the ID given
     *
     * @param id                    The Clients ID
     * @param updateEmployeePayload The RequestBody of what needs to be changed
     * @return ResponseEntity with Code
     */
    @Secured({AccessRoles.ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @ModelAttribute EmployeeModel updateEmployeePayload) {
        userRepository.findById(id).ifPresentOrElse(
                (employee) -> employee.updateSelf(updateEmployeePayload),
                () -> {
                    userRepository.save(updateEmployeePayload.toEmployee());
                }
        );
        return ResponseEntity.ok().build();
    }

    @Secured({AccessRoles.ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteClient(@PathVariable Long id) {
        userRepository.deleteById(id);
        return userRepository.findById(id).isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();
    }

    @Secured({AccessRoles.USER})
    @GetMapping("/{id}")
    public Employee getClient(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Secured({AccessRoles.USER})
    @GetMapping("/{username}")
    public Employee findByUsername(@PathVariable String username) {
        System.out.println("username");
        return userRepository.findByUsername(username);
    }
}
