package com.swa.properSpring.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swa.properSpring.customer.Customer;
import com.swa.properSpring.models.EmployeeModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Size(min = 8, max = 64)
    @Pattern(regexp = "[a-zA-Z0-9.-]+")
    private String username;
    @NotBlank
    @Size(min = 8, max = 64)
    private String password;
    @NotBlank
    @Size(min = 8, max = 64)
    @Email
    private String email;
    @NotBlank
    @Size(min = 2, max = 64)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String first_name;
    @NotBlank
    @Size(min = 2, max = 64)
    @Pattern(regexp = "[a-zA-Z0-9']+")
    private String last_name;
    @NotBlank
    @Size(min = 8, max = 64)
    private String phone1;
    private String phone2;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdOn;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }

    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }


    public String getFirst_name() {
        return first_name;
    }


    public String getLast_name() {
        return last_name;
    }


    public String getPhone1() {
        return phone1;
    }


    public String getPhone2() {
        return phone2;
    }

    public java.util.Date getCreatedOn() {
        return createdOn;
    }

    protected Employee() {

    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateSelf(@NotNull EmployeeModel updateEmployeePayload) {
        Employee updateEmployee = updateEmployeePayload.toEmployee();
        Arrays.stream(updateEmployee.getClass().getDeclaredFields()).forEach((field -> {
            try {
                this.getClass().getDeclaredField(field.getName()).set(this,updateEmployee.getClass().getDeclaredField(field.getName()).get(updateEmployee));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static class Builder {
        private final Employee employee;

        public Builder(String username, String password) {
            employee = new Employee();
            employee.username = username;
            employee.password = password;
            employee.roles = new HashSet<>();
        }
        public Employee.Builder setCompany(Customer customer){
            employee.setCustomer(customer);
            return this;
        }

        public void setPassword(String encodedPassword) {
            employee.password = encodedPassword;
        }

        public String getPassword() {
            return this.employee.getPassword();
        }


        public Builder id(Long id) {
            employee.id = id;
            return this;
        }

        public Builder roles(Set<String> roles) {
            employee.roles = new HashSet<>(roles);
            return this;
        }

        public Builder addRole(String role) {
            employee.roles.add(role);
            return this;
        }

        public Builder addEmail(String email) {
            employee.email = email;
            return this;
        }

        public Builder addPhone(String phoneNumber) {
            if (employee.phone1 == null) {
                employee.phone1 = phoneNumber;
            } else {
                employee.phone2 = phoneNumber;
            }
            return this;
        }

        public Builder addName(String firstName, String lastName) {
            employee.first_name = firstName;
            employee.last_name = lastName;
            return this;
        }

        public Employee build() {
            return employee;
        }

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
