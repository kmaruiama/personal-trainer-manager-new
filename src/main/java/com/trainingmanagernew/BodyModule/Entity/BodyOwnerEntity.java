package com.trainingmanagernew.BodyModule.Entity;

import com.trainingmanagernew.BodyModule.Entity.Workout.ProgramEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class BodyOwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    //customerEntity
    @Column(nullable = false, unique = true)
    private UUID customerId;

    //userEntity
    @Column(nullable = false)
    private UUID customerOwnerId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bodyOwnerEntity")
    private List<HeightEntity> heightEntityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bodyOwnerEntity")
    private List<BodyEntity> bodyEntityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bodyOwnerEntity")
    private List<ProgramEntity> programEntityList = new ArrayList<>();

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerOwnerId() {
        return customerOwnerId;
    }

    public void setCustomerOwnerId(UUID customerOwnerId) {
        this.customerOwnerId = customerOwnerId;
    }

    public List<HeightEntity> getHeightEntityList() {
        return heightEntityList;
    }

    public void setHeightEntityList(List<HeightEntity> heightEntityList) {
        this.heightEntityList = heightEntityList;
    }

    public List<BodyEntity> getBodyEntityList() {
        return bodyEntityList;
    }

    public void setBodyEntityList(List<BodyEntity> bodyEntityList) {
        this.bodyEntityList = bodyEntityList;
    }
}
