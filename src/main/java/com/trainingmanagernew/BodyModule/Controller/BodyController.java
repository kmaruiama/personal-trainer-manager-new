package com.trainingmanagernew.BodyModule.Controller;

import com.trainingmanagernew.BodyModule.Dto.Body.BodyGetDto;
import com.trainingmanagernew.BodyModule.Dto.Body.BodyPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Service.BodyEntityService.Delete.DeleteBodyEntityService;
import com.trainingmanagernew.BodyModule.Service.BodyEntityService.Get.GetAllBodyEntitiesById;
import com.trainingmanagernew.BodyModule.Service.BodyEntityService.Get.GetLastBodyEntityByOwnerId;
import com.trainingmanagernew.BodyModule.Service.BodyEntityService.Post.AddNewBodyRecordService;
import com.trainingmanagernew.BodyModule.Service.BodyEntityService.Put.EditBodyEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/body/body")
@Validated
public class BodyController {
    private final AddNewBodyRecordService addNewBodyRecordService;
    private final GetAllBodyEntitiesById getAllBodyEntitiesById;
    private final EditBodyEntityService editBodyEntityService;
    private final DeleteBodyEntityService deleteBodyEntityService;
    private final GetLastBodyEntityByOwnerId getLastBodyEntityByOwnerId;

    public BodyController(AddNewBodyRecordService addNewBodyRecordService, GetAllBodyEntitiesById getAllBodyEntitiesById, EditBodyEntityService editBodyEntityService, DeleteBodyEntityService deleteBodyEntityService, GetLastBodyEntityByOwnerId getLastBodyEntityByOwnerId) {
        this.addNewBodyRecordService = addNewBodyRecordService;
        this.getAllBodyEntitiesById = getAllBodyEntitiesById;
        this.editBodyEntityService = editBodyEntityService;
        this.deleteBodyEntityService = deleteBodyEntityService;
        this.getLastBodyEntityByOwnerId = getLastBodyEntityByOwnerId;
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

    @GetMapping("/last")
    public ResponseEntity<BodyGetDto> returnLastBodyCompositionInput(UUID id){
          BodyGetDto bodyGetDto = getLastBodyEntityByOwnerId.get(id);
          return ResponseEntity.status(HttpStatus.OK).body(bodyGetDto);
    }
}
