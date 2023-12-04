package com.swa.properSpring.customer;

import com.swa.properSpring.contract.Contract;
import com.swa.properSpring.user.Employee;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long customer_id;

    private String customerName;
    private String addressDetailA;
    private String addressDetailB;
    @OneToMany()
    private final List<Employee> users  = new ArrayList<>();
    @OneToMany()
    private final List<Contract> contracts = new ArrayList<>();

    public String getCustomerName() {
        return customerName;
    }

    public List<Employee> getUsers() {
        return users;
    }



    public String getAddressDetailA() {
        return addressDetailA;
    }

    public String getAddressDetailB() {
        return addressDetailB;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public static class Builder{
        private final Customer customer;

        public Builder() {
            this.customer = new Customer();
        }
        public Customer.Builder setCustomerName(String customerName){
            this.customer.customerName = customerName;
            return this;
        }
        public Customer.Builder setAddressDetail(String addressDetail){
            if(customer.addressDetailA != null){
                customer.addressDetailB = addressDetail;
                return this;
            }
            if(customer.addressDetailB != null){
                return this;
            }
            customer.addressDetailB = addressDetail;
            return this;
        }
        public Customer.Builder addUser(Employee employee){
            customer.users.add(employee);
            return this;
        }
        public Customer build(){
            return this.customer;
        }
    }
}
