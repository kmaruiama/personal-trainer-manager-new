package com.trainingmanagernew.BodyModule.Service.Workout;

import com.trainingmanagernew.BodyModule.Dto.Workout.WorkoutPostDto;

public interface AddNewWorkout {
    void add(WorkoutPostDto workoutPostDto, String authHeader);
}
