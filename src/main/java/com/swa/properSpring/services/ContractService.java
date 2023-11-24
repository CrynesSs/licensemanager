package com.swa.properSpring.services;

import com.swa.properSpring.contract.Contract;
import com.swa.properSpring.contract.ContractRepository;
import com.swa.properSpring.customer.Customer;
import com.swa.properSpring.customer.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    public ContractService(CustomerRepository customerRepository, ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
        this.customerRepository = customerRepository;
    }

    public void saveContractWithCustomer(UUID customerId, Contract contract) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));

        contract.setCustomer(customer);
        contractRepository.save(contract);
    }
}
