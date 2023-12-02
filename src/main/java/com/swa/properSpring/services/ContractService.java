package com.swa.properSpring.services;

import com.swa.properSpring.contract.Contract;
import com.swa.properSpring.contract.ContractRepository;
import com.swa.properSpring.customer.Customer;
import com.swa.properSpring.customer.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    public ContractService(CustomerRepository customerRepository, ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
        this.customerRepository = customerRepository;
    }

    public void saveContractWithCustomer(Long customerId, Contract contract) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));

        customer.getContracts().add(contract);
        contract.setCustomer(customer);
        customerRepository.save(customer);
        contractRepository.save(contract);
    }
}
