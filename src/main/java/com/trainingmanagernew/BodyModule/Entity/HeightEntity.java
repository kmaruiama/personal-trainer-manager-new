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
}
