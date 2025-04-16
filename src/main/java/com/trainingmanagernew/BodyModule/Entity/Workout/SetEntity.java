package com.trainingmanagernew.BodyModule.Entity.Workout;

import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Entity.Exercise.Exercise;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class SetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private int repetitions;

    @Column(nullable = false)
    private float weight;

    @ManyToOne(fetch = FetchType.LAZY)
    private BodyOwnerEntity bodyOwnerEntity;

    //não vou transformar em relacionamento pela quantidade absurda de inputs que essa entidade vai receber
    @Column(nullable = false)
    private UUID exerciseId;
}
