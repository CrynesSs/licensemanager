package com.swa.properSpring.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public Set<String> getRoles() {
        return roles;
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



    public String getCompany() {
        return company;
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
    private String username;
    private String password;
    private String email;
    private String company;
    private String first_name;
    private String last_name;
    private String phone1;
    private String phone2;
    private java.util.Date createdOn;

    protected Employee(){

    }


    public Employee(String username, String password, String email, String company, String first_name, String last_name, String phone1, String phone2, HashSet<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.company = company;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.roles = roles;
        this.createdOn = new java.util.Date();
    }
    public static class Builder {

        private final Employee employee;

        public Builder(String username, String password) {
            employee = new Employee();
            employee.username = username;
            employee.password = password;
            employee.createdOn = new Date();
            employee.roles = new HashSet<>();
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
        public Builder addEmail(String email){
            employee.email = email;
            return this;
        }
        public Builder addCompany(String company){
            employee.company = company;
            return this;
        }
        public Builder addPhone(String phoneNumber){
            if(employee.phone1 == null){
                employee.phone1 = phoneNumber;
            }else{
                employee.phone2 = phoneNumber;
            }
            return this;
        }
        public Builder addName(String firstName,String lastName){
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
