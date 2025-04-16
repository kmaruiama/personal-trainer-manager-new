package com.trainingmanagernew.BodyModule.Entity.Exercise;

import com.trainingmanagernew.BodyModule.Entity.Exercise.Enum.ExerciseEnvironmentType;
import com.trainingmanagernew.BodyModule.Entity.Exercise.Enum.MajorGroupType;
import com.trainingmanagernew.BodyModule.Entity.Exercise.Enum.PositionType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private ExerciseEnvironmentType exerciseEnvironmentType;

    @Column(nullable = false)
    private MajorGroupType majorGroupType;

    @Column(nullable = false)
    private PositionType positionType;
}
