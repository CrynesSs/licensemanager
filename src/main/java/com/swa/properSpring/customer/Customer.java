package com.swa.properSpring.customer;

import com.swa.properSpring.contract.Contract;
import com.swa.properSpring.user.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String customerName;
    @NotBlank
    private String addressDetailA;
    @NotBlank
    private String addressDetailB;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer",orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<Employee> users  = new HashSet<>();

    public void addUser(Employee employee){
        users.add(employee);
        employee.setCustomer(this);
    }
    public void removeUser(Employee employee){
        users.remove(employee);
        employee.setCustomer(null);
    }
    @OneToMany(orphanRemoval = true,cascade = CascadeType.REMOVE)
    private final Set<Contract> contracts = new HashSet<>();

    public String getCustomerName() {
        return customerName;
    }

    public Set<Employee> getEmployees() {
        return users;
    }

    public String getAddressDetailA() {
        return addressDetailA;
    }

    public String getAddressDetailB() {
        return addressDetailB;
    }

    public Set<Contract> getContracts() {
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
            if(customer.addressDetailA == null){
                customer.addressDetailA = addressDetail;
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
