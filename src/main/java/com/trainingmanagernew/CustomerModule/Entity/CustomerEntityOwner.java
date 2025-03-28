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
    private UUID localOwnerId;

    @OneToMany(mappedBy = "localOwnerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerEntity> customerEntityList = new ArrayList<>();

    public UUID getOwnerId() {
        return ownerId;
    }

    public UUID getLocalOwnerId() {
        return localOwnerId;
    }

    public void setLocalOwnerId(UUID localOwnerId) {
        this.localOwnerId = localOwnerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
