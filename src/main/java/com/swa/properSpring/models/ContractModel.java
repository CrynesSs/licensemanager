package com.swa.properSpring.models;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class ContractModel {
    private long id;
    @NotBlank
    private String version;
    @NotBlank
    private Date contractStart;
    @NotBlank
    private Date contractEnd;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    private String customer;

    public ContractModel(String version, Date contractStart, Date contractEnd, String customer, String instance) {
        this.version = version;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.customer = customer;
        this.instance = instance;
    }
    public ContractModel(long id,String version, Date contractStart, Date contractEnd, String customer, String instance) {
        this.id = id;
        this.version = version;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.customer = customer;
        this.instance = instance;
    }

    private String instance;


    public long getId() {
        return id;
    }
}
