package com.swa.properSpring.user;

import com.swa.properSpring.models.EmployeeModel;
import com.swa.security.AccessRoles;
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
    @Secured({AccessRoles.USER})
    public List<Employee> getUsers(Authentication authentication) {
        authentication.isAuthenticated();
        return userRepository.findAll();
    }

    @Secured({AccessRoles.ADMIN})
    @PostMapping
    public ResponseEntity<Employee> createClient(@RequestBody Employee client) {
        Employee savedClient = userRepository.save(client);
        try {
            return ResponseEntity.created(new URI("/clients/" + savedClient.getId())).body(savedClient);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the Client with the ID given
     *
     * @param id The Clients ID
     * @param updateEmployeePayload The RequestBody of what needs to be changed
     * @return ResponseEntity with Code
     */
    @Secured({AccessRoles.ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateClient(@PathVariable Long id, @ModelAttribute EmployeeModel updateEmployeePayload) {
        Employee currentClient = userRepository.findById(id).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(currentClient);
    }

    @Secured({AccessRoles.ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteClient(@PathVariable Long id) {

        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Secured({AccessRoles.USER})
    @GetMapping("/{id}")
    public Employee getClient(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    @Secured({AccessRoles.USER})
    @GetMapping("/{username}")
    public Employee findByUsername(@PathVariable String username){
        System.out.println("username");
        return userRepository.findByUsername(username);
    }
}
