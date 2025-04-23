package com.trainingmanagernew.BodyModule.Entity.Exercise;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Muscle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String muscle;
}
