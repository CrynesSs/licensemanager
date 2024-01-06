package com.swa.properSpring.models;

import com.swa.properSpring.customer.Customer;
import jakarta.validation.constraints.NotBlank;

public class CustomerModel {
    @NotBlank
    private final String customerName;
    @NotBlank
    private final String addressDetailA;

    public String getCustomerName() {
        return customerName;
    }

    public String getAddressDetailA() {
        return addressDetailA;
    }

    public String getAddressDetailB() {
        return addressDetailB;
    }

    public void setAddressDetailB(String addressDetailB) {
        this.addressDetailB = addressDetailB;
    }

    @NotBlank
    private String addressDetailB;

    public CustomerModel(String customerName, String addressDetailA, String addressDetailB) {
        this.customerName = customerName;
        this.addressDetailA = addressDetailA;
        this.addressDetailB = addressDetailB;
    }



    public static CustomerModel fromCustomer(Customer customer){
        return new CustomerModel(customer.getCustomerName(), customer.getAddressDetailA(), customer.getAddressDetailB());
    }
}
