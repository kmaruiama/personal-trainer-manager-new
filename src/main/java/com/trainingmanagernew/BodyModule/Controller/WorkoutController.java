package com.trainingmanagernew.BodyModule.Controller;

import com.trainingmanagernew.BodyModule.Dto.Workout.WorkoutPostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/api/body/workout")
public class WorkoutController {
    /*@PostMapping
    ResponseEntity<Map<String, String>> postNewWorkout (@RequestBody WorkoutPostDto workoutPostDto,
                                                        @RequestHeader ("Authorization") String authHeader){

    }*/
}
