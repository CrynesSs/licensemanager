package com.swa.properSpring.contract;

import com.swa.properSpring.customer.Customer;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Contract {
    @Id
    @GeneratedValue
    private Long id;
    private float Version;
    private Date contractStart;
    private Date contractEnd;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    public void setId(Long id) {
        this.id = id;
    }

    public float getVersion() {
        return Version;
    }

    public void setVersion(float version) {
        Version = version;
    }

    public Date getContractStart() {
        return contractStart;
    }

    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }



    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public Long getId() {
        return id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
