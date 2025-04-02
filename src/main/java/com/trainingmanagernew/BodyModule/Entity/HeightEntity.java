package com.trainingmanagernew.BodyModule.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class HeightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "bodyOwner_id")
    private BodyOwnerEntity bodyOwnerEntity;

    @Column(nullable = false)
    private float height;

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

    public BodyOwnerEntity getBodyOwnerEntity() {
        return bodyOwnerEntity;
    }

    public void setBodyOwnerEntity(BodyOwnerEntity bodyOwnerEntity) {
        this.bodyOwnerEntity = bodyOwnerEntity;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
