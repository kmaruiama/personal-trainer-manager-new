package com.trainingmanagernew.CustomerModule.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class CustomerEntityOwner {

    private UUID ownerId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "localOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerEntity> customerEntityList = new ArrayList<>();

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
