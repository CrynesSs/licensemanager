package com.swa.properSpring.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customer_id;
    private String addressDetailA;
    private String addressDetailB;


    public void setCustomer_id(UUID customer_id) {
        this.customer_id = customer_id;
    }

    public UUID getCustomer_id() {
        return customer_id;
    }

    public String getAddressDetailA() {
        return addressDetailA;
    }

    public void setAddressDetailA(String addressDetailA) {
        this.addressDetailA = addressDetailA;
    }

    public String getAddressDetailB() {
        return addressDetailB;
    }

    public void setAddressDetailB(String addressDetailB) {
        this.addressDetailB = addressDetailB;
    }
}
