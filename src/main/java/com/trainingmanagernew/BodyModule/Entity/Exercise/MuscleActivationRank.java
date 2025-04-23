package com.trainingmanagernew.BodyModule.Entity.Exercise;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;

@Entity
public class MuscleActivationRank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Exercise exercise;

    @Column(nullable = false)
    @Min(1)
    @Max(10)
    private int intensity;

    @ManyToOne
    private Muscle muscle;
}
