package com.trainingmanagernew.BodyModule.Entity.Exercise;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class DefaultExercise extends Exercise {
    @OneToMany(cascade = CascadeType.ALL)
    private List<MuscleActivationRank> muscleActivationRank;

    @OneToOne
    private TagStructure tagStructure;

    @OneToOne
    private TagWords tagWords;
}
