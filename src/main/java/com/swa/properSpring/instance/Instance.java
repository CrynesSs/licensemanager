package com.swa.properSpring.instance;

import com.swa.properSpring.contract.Contract;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Instance {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String ip;

    private char type;

    @OneToOne
    private Contract serviceContract;

}
