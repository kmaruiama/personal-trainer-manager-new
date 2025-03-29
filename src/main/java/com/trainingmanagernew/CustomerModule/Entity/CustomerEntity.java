package com.trainingmanagernew.CustomerModule.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

//I've realized that asking for a customer address would be weird in a person to person interaction xD
@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    private String name;

    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "localOwner_id")
    private CustomerEntityOwner localOwner;

    @Nullable()
    public CustomerEntityOwner getLocalOwner() {
        return localOwner;
    }

    public void setLocalOwner(CustomerEntityOwner localOwner) {
        this.localOwner = localOwner;
    }

    //I won't be adding relationships in the entities between modules to keep things decoupled
    private UUID bodyId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
