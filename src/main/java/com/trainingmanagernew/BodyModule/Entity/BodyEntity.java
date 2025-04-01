package com.trainingmanagernew.BodyModule.Entity;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.UUID;

@Entity
public class BodyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private float weight;

    private float bodyFat;

    @ManyToOne
    @JoinColumn(name = "bodyOwner_id")
    private BodyOwnerEntity bodyOwnerEntity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(float bodyFat) {
        this.bodyFat = bodyFat;
    }

    public BodyOwnerEntity getBodyOwnerEntity() {
        return bodyOwnerEntity;
    }

    public void setBodyOwnerEntity(BodyOwnerEntity bodyOwnerEntity) {
        this.bodyOwnerEntity = bodyOwnerEntity;
    }
}
