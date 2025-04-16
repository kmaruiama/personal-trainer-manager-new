package com.trainingmanagernew.BodyModule.Controller;

import com.trainingmanagernew.BodyModule.Dto.Height.HeightGetDto;
import com.trainingmanagernew.BodyModule.Dto.Height.HeightPostDto;
import com.trainingmanagernew.BodyModule.Service.HeightEntityService.Get.GetAllHeightEntitiesById;
import com.trainingmanagernew.BodyModule.Service.HeightEntityService.Post.AddNewHeightRecordService;
import com.trainingmanagernew.BodyModule.Service.HeightEntityService.Put.EditHeightEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/body/height")
@Validated
public class HeightController
{
    private final AddNewHeightRecordService addNewHeightRecordService;
    private final GetAllHeightEntitiesById getAllHeightEntitiesById;
    private final EditHeightEntityService editHeightEntityService;

    public HeightController(AddNewHeightRecordService addNewHeightRecordService, GetAllHeightEntitiesById getAllHeightEntitiesById, EditHeightEntityService editHeightEntityService) {
        this.addNewHeightRecordService = addNewHeightRecordService;
        this.getAllHeightEntitiesById = getAllHeightEntitiesById;
        this.editHeightEntityService = editHeightEntityService;
    }

    @PostMapping("/new")
    ResponseEntity<Map<String,String>> postNewHeightInfo(@Valid @RequestBody HeightPostDto heightPostDto,
                                                         @RequestHeader ("Authorization") String authHeader){
        addNewHeightRecordService.add(heightPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Informação de altura atualizada com sucesso"));
    }

    @GetMapping("/all")
    ResponseEntity<List<HeightGetDto>> getAllBodyRecordsById(@RequestHeader UUID id,
                                                        @RequestHeader ("Authorization") String authHeader){
        List<HeightGetDto> heightGetDtoList = getAllHeightEntitiesById.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(heightGetDtoList);
    }

    @PutMapping("/edit")
    public ResponseEntity<Map<String, String>> editBodyById(@Valid HeightPostDto heightPostDto,
                                                            @RequestHeader("Authorization") String authHeader){
        editHeightEntityService.edit(heightPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Composição corporal editada com sucesso"));
    }
}
