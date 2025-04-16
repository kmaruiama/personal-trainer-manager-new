package com.trainingmanagernew.BodyModule.Service.Workout;

import com.trainingmanagernew.BodyModule.Dto.Workout.SetPostDto;

import java.util.UUID;

public interface AddNewSet {
    void add (UUID exerciseId, SetPostDto setPostDto, String authHeader);
}
