package com.trainingmanagernew.BodyModule.Controller;

import com.trainingmanagernew.BodyModule.Dto.Height.HeightGetDto;
import com.trainingmanagernew.BodyModule.Dto.Height.HeightPostDto;
import com.trainingmanagernew.BodyModule.Dto.Shared.DeleteResourceDto;
import com.trainingmanagernew.BodyModule.Service.HeightEntityService.Delete.DeleteHeightEntityService;
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
    private final DeleteHeightEntityService deleteHeightEntityService;

    public HeightController(AddNewHeightRecordService addNewHeightRecordService, GetAllHeightEntitiesById getAllHeightEntitiesById, EditHeightEntityService editHeightEntityService, DeleteHeightEntityService deleteHeightEntityService) {
        this.addNewHeightRecordService = addNewHeightRecordService;
        this.getAllHeightEntitiesById = getAllHeightEntitiesById;
        this.editHeightEntityService = editHeightEntityService;
        this.deleteHeightEntityService = deleteHeightEntityService;
    }

    @PostMapping("/new")
    ResponseEntity<Map<String,String>> postNewHeightInfo(@Valid @RequestBody HeightPostDto heightPostDto,
                                                         @RequestHeader ("Authorization") String authHeader){
        addNewHeightRecordService.add(heightPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Informação de altura atualizada com sucesso"));
    }

    @GetMapping("/all")
    ResponseEntity<List<HeightGetDto>> getAllHeightRecordsById(@RequestHeader UUID id,
                                                        @RequestHeader ("Authorization") String authHeader){
        List<HeightGetDto> heightGetDtoList = getAllHeightEntitiesById.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(heightGetDtoList);
    }

    @PutMapping("/edit")
    public ResponseEntity<Map<String, String>> editHeightById(@Valid @RequestBody HeightPostDto heightPostDto,
                                                            @RequestHeader("Authorization") String authHeader){
        editHeightEntityService.edit(heightPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Composição corporal editada com sucesso"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteHeightById(@RequestParam UUID id,
                                                                @RequestHeader("Authorization") String authHeader){
        DeleteResourceDto deleteResourceDto = new DeleteResourceDto();
        deleteResourceDto.setId(id);
        deleteResourceDto.setType("HEIGHT");
        deleteHeightEntityService.delete(deleteResourceDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Composição corporal editada com sucesso"));
    }
}
