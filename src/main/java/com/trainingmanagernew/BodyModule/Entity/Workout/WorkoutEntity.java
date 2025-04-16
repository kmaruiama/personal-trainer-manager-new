package com.trainingmanagernew.BodyModule.Entity.Workout;

import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class WorkoutEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (nullable = false)
    private UUID id;

    @Column (nullable = false)
    private String name;

    //revisar os fetchtypes
    @ManyToOne(fetch = FetchType.LAZY)
    private BodyOwnerEntity bodyOwnerEntity;


    @OneToMany(mappedBy = "workoutEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SetEntity> setEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private ProgramEntity programEntity;

    @Column (nullable = false)
    private LocalDate localDate;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BodyOwnerEntity getBodyOwnerEntity() {
        return bodyOwnerEntity;
    }

    public void setBodyOwnerEntity(BodyOwnerEntity bodyOwnerEntity) {
        this.bodyOwnerEntity = bodyOwnerEntity;
    }

    public ProgramEntity getProgramEntity() {
        return programEntity;
    }

    public void setProgramEntity(ProgramEntity programEntity) {
        this.programEntity = programEntity;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
