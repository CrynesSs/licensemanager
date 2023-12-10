package com.swa.properSpring.instance;

import com.swa.properSpring.contract.Contract;
import com.swa.properSpring.contract.ContractRepository;
import com.swa.security.AccessRoles;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/instance")
public class InstanceController {
    private final InstanceRepository repository;

    public InstanceController(InstanceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Secured({AccessRoles.USER})
    public List<Instance> getContracts(){
        Sort sort = Sort.by(Sort.Direction.ASC,"Version");
        return repository.findAll(sort);
    }
    @GetMapping("/{id}")
    @Secured({AccessRoles.USER})
    public Instance getContractByID(@PathVariable Long id){
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    @Secured({AccessRoles.ADMIN})
    public ResponseEntity<?> postContract(@ModelAttribute Instance contract){
        return ResponseEntity.ok().build();
    }
    @PutMapping
    @Secured({AccessRoles.ADMIN})
    public ResponseEntity<?> putContract(@ModelAttribute Instance contract){
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    @Secured({AccessRoles.ADMIN})
    public ResponseEntity<?> deleteContract(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
