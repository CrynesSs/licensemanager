package com.swa.properSpring.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String email;
    private String company;
    private String first_name;
    private String last_name;
    private String phone1;
    private String phone2;

    protected User(){

    }

    public User(String username, String password, String email, String company, String first_name, String last_name, String phone1, String phone2) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.company = company;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone1 = phone1;
        this.phone2 = phone2;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
