package com.trainingmanagernew.BodyModule.Controller;

import com.trainingmanagernew.BodyModule.Dto.Height.HeightPostDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/body/height")
@Validated
public class HeightController
{
    /*@PostMapping("/new")
    ResponseEntity<Map<String,String>> postNewHeightInfo(@Valid @RequestBody HeightPostDto,
                                                         @RequestHeader ("Authorization") String authHeader){

    }*/

}
