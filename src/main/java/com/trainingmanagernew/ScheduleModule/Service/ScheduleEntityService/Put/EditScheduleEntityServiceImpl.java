package com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Put;

import com.trainingmanagernew.ScheduleModule.Dto.SchedulePostDto;
import com.trainingmanagernew.ScheduleModule.Dto.ValidateScheduleDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import com.trainingmanagernew.ScheduleModule.Service.ValidateScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EditScheduleEntityServiceImpl implements EditScheduleEntityService{
    private final ScheduleRepository scheduleRepository;
    private final ValidateScheduleService validateScheduleService;

    public EditScheduleEntityServiceImpl(ScheduleRepository scheduleRepository, ValidateScheduleService validateScheduleService) {
        this.scheduleRepository = scheduleRepository;
        this.validateScheduleService = validateScheduleService;
    }

    @Override
    @Transactional
    public void edit(SchedulePostDto schedulePostDto) {
        validate(schedulePostDto);
        Optional<ScheduleEntity> scheduleEntityOptional = scheduleRepository.findById(schedulePostDto.getOptionalScheduleId());
        ScheduleEntity scheduleEntity;
        if (scheduleEntityOptional.isPresent()){
            scheduleEntity = scheduleEntityOptional.get();
        }
        else {
            throw new ScheduleCustomExceptions.ScheduleEntityNotFoundException();
        }
        changeParameters(schedulePostDto, scheduleEntity);
        scheduleRepository.save(scheduleEntity);
    }

    private void changeParameters(SchedulePostDto schedulePostDto, ScheduleEntity scheduleEntity){
        scheduleEntity.setHourEnd(schedulePostDto.getHourEnd());
        scheduleEntity.setHourStart(schedulePostDto.getHourStart());
        scheduleEntity.setDayOfTheWeek(schedulePostDto.getDayOfTheWeek());
    }

    private void validate(SchedulePostDto schedulePostDto){
        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setHourStart(schedulePostDto.getHourStart());
        validateScheduleDto.setHourEnd(schedulePostDto.getHourEnd());
        validateScheduleDto.setDayOfTheWeek(schedulePostDto.getDayOfTheWeek());
        validateScheduleService.validate(validateScheduleDto);
    }
}
