package com.trainingmanagernew.BodyModule.Entity.Exercise;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;
}
