package com.trainingmanagernew.BodyModule.Entity.Workout;

import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Entity.Workout.WorkoutEntity;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class ProgramEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<WorkoutEntity> workoutEntityList;

    @ManyToOne
    private BodyOwnerEntity bodyOwnerEntity;

    @Column(nullable = false)
    boolean blueprint;

}
