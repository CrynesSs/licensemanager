package com.swa.properSpring.contract;

import com.swa.properSpring.customer.Customer;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Contract {
    @Id
    @GeneratedValue
    private Long id;
    private String Version;
    private Date contractStart;
    private Date contractEnd;
    @ManyToOne
    private Customer customer;
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

    public Long getId() {
        return id;
    }

}
