package com.trainingmanagernew.BodyModule.Dto.Workout;

import java.util.List;
import java.util.UUID;

public class WorkoutPostDto {
    private UUID programId;
    private UUID id;
    private String name;
    private UUID bodyOwnerId;
    private List<ExercisePostDto> exerciseDtoList;
    private boolean blueprint;
}
