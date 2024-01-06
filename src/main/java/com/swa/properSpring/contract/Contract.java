package com.swa.properSpring.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swa.properSpring.customer.Customer;
import com.swa.properSpring.instance.Instance;
import com.swa.properSpring.models.ContractModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Contract {
    @Id
    @GeneratedValue
    private Long id;

    public Contract() {

    }
    @Size(min = 4, max = 8)
    @Pattern(regexp = "\\d{3}\\.\\d{4}")
    @NotBlank
    private String Version;
    @NotBlank
    private Date contractStart;
    @NotBlank
    private Date contractEnd;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
    private Instance instance;

    public void setId(Long id) {
        this.id = id;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }


    public String getVersion() {
        return Version;
    }

    public Date getContractStart() {
        return contractStart;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Contract(String version, Date contractStart, Date contractEnd, Customer customer, Instance instance) {
        Version = version;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.customer = customer;
        this.instance = instance;
    }

    public Long getId() {
        return id;
    }

    public void updateSelf(ContractModel contract) {

    }

    public static class Builder {

    }
}
