package com.swa.properSpring.instance;

import com.swa.properSpring.contract.Contract;
import jakarta.persistence.*;

@Entity
public class Instance {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String ip;

    private char type;

    public Instance() {

    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Contract getServiceContract() {
        return serviceContract;
    }

    public void setServiceContract(Contract serviceContract) {
        this.serviceContract = serviceContract;
    }

    public Instance(String name, String ip, char type, Contract serviceContract) {
        this.name = name;
        this.ip = ip;
        this.type = type;
        this.serviceContract = serviceContract;
    }

    @OneToOne(cascade = CascadeType.DETACH)
    private Contract serviceContract;

    public static class Builder {

    }
}
