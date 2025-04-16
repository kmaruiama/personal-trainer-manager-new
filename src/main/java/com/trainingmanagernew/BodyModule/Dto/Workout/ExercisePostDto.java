package com.trainingmanagernew.BodyModule.Dto.Workout;

import java.util.List;
import java.util.UUID;

public class ExercisePostDto {
    private UUID id;
    private UUID exerciseId;
    private List<SetPostDto> setDtoList;
}
