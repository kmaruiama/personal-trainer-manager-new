package com.trainingmanagernew.ScheduleModule;

import com.trainingmanagernew.ScheduleModule.Dto.ScheduleGetDto;
import com.trainingmanagernew.ScheduleModule.Dto.SchedulePostDto;
import com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Delete.DeleteScheduleEntityService;
import com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Get.GetAllScheduleEntitiesByScheduleOwnerId;
import com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Post.AddNewScheduleService;
import com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Put.EditScheduleEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("api/schedule/schedule")
public class ScheduleController {
    private final GetAllScheduleEntitiesByScheduleOwnerId getAllScheduleEntitiesByScheduleOwnerId;
    private final AddNewScheduleService addNewScheduleService;
    private final DeleteScheduleEntityService deleteScheduleEntityService;
    private final EditScheduleEntityService editScheduleEntityService;

    public ScheduleController(GetAllScheduleEntitiesByScheduleOwnerId getAllScheduleEntitiesByScheduleOwnerId, AddNewScheduleService addNewScheduleService, DeleteScheduleEntityService deleteScheduleEntityService, EditScheduleEntityService editScheduleEntityService) {
        this.getAllScheduleEntitiesByScheduleOwnerId = getAllScheduleEntitiesByScheduleOwnerId;
        this.addNewScheduleService = addNewScheduleService;
        this.deleteScheduleEntityService = deleteScheduleEntityService;
        this.editScheduleEntityService = editScheduleEntityService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduleGetDto>> getAllScheduleRecordsById(@RequestParam UUID id,
                                                                          @RequestHeader ("Authorization") String authHeader){
        List<ScheduleGetDto> scheduleGetDtoList = getAllScheduleEntitiesByScheduleOwnerId.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleGetDtoList);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, String>> postNewScheduleInfo(@RequestBody SchedulePostDto schedulePostDto,
                                                                   @RequestHeader ("Authorization") String authHeader){
        addNewScheduleService.add(schedulePostDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Novo agendamento registrado com sucesso"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteScheduleById(@RequestParam UUID id,
                                                                  @RequestHeader ("Authorization") String authHeader){
        deleteScheduleEntityService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Novo agendamento registrado com sucesso"));
    }

    @PutMapping("/edit")
    public ResponseEntity<Map<String, String>> editBodyById(@RequestBody SchedulePostDto schedulePostDto,
                                                            @RequestHeader("Authorization") String authHeader){
        editScheduleEntityService.edit(schedulePostDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Novo agendamento editado com sucesso"));
    }
}
