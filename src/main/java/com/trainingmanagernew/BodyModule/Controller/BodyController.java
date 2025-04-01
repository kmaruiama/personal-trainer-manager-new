package com.trainingmanagernew.BodyModule.Controller;

import com.trainingmanagernew.BodyModule.Dto.BodyGetDto;
import com.trainingmanagernew.BodyModule.Dto.BodyPostDto;
import com.trainingmanagernew.BodyModule.Service.Delete.DeleteBodyEntityService;
import com.trainingmanagernew.BodyModule.Service.Get.GetAllBodyEntitiesById;
import com.trainingmanagernew.BodyModule.Service.Post.AddNewBodyRecordService;
import com.trainingmanagernew.BodyModule.Service.Put.EditBodyEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/body")
@Validated
public class BodyController {
    private final AddNewBodyRecordService addNewBodyRecordService;
    private final GetAllBodyEntitiesById getAllBodyEntitiesById;
    private final EditBodyEntityService editBodyEntityService;
    private final DeleteBodyEntityService deleteBodyEntityService;

    public BodyController(AddNewBodyRecordService addNewBodyRecordService, GetAllBodyEntitiesById getAllBodyEntitiesById, EditBodyEntityService editBodyEntityService, DeleteBodyEntityService deleteBodyEntityService) {
        this.addNewBodyRecordService = addNewBodyRecordService;
        this.getAllBodyEntitiesById = getAllBodyEntitiesById;
        this.editBodyEntityService = editBodyEntityService;
        this.deleteBodyEntityService = deleteBodyEntityService;
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, String>> postNewBodyInfo(@Valid @RequestBody BodyPostDto bodyPostDto,
                                                               @RequestHeader("Authorization") String authHeader){
        addNewBodyRecordService.add(bodyPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Composição corporal registrada com sucesso"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BodyGetDto>> getAllBodyRecordsById(@RequestParam UUID id,
                                                                  @RequestHeader("Authorization") String authHeader){
        List<BodyGetDto> bodyGetDtoList = getAllBodyEntitiesById.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(bodyGetDtoList);
    }

    @PutMapping("/edit")
    public ResponseEntity<Map<String, String>> editBodyById(@Valid BodyPostDto bodyPostDto,
                                                            @RequestHeader("Authorization") String authHeader){
        editBodyEntityService.edit(bodyPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Composição corporal editada com sucesso"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteBodyById(@RequestParam UUID id,
                                                              @RequestHeader("Authorization") String authHeader){
        deleteBodyEntityService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Composição corporal deletada com sucesso"));
    }
}
