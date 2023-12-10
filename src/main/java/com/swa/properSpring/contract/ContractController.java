package com.swa.properSpring.contract;

import com.swa.security.AccessRoles;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/contract")
public class ContractController {

    private final ContractRepository repository;

    public ContractController(ContractRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Secured({AccessRoles.USER})
    public List<Contract> getContracts(){
        Sort sort = Sort.by(Sort.Direction.ASC,"Version");
        return repository.findAll(sort);
    }
    @GetMapping("/{id}")
    @Secured({AccessRoles.USER})
    public Contract getContractByID(@PathVariable Long id){
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    @Secured({AccessRoles.ADMIN})
    public ResponseEntity<?> postContract(@ModelAttribute Contract contract){
        return ResponseEntity.ok().build();
    }
    @PutMapping
    @Secured({AccessRoles.ADMIN})
    public ResponseEntity<?> putContract(@ModelAttribute Contract contract){
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    @Secured({AccessRoles.ADMIN})
    public ResponseEntity<?> deleteContract(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
