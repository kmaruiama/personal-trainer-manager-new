package com.trainingmanagernew.ScheduleModule.Service;

import com.trainingmanagernew.ScheduleModule.Dto.ValidateScheduleDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ValidateScheduleServiceImpl implements ValidateScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ValidateScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void validate(ValidateScheduleDto validateScheduleDto) {
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.findByScheduleOwnerEntity_Id(validateScheduleDto.getOwnerId());
        validateAvaliability(validateScheduleDto, scheduleEntityList);
        validateTime(validateScheduleDto.getHourStart(), validateScheduleDto.getHourEnd());
        validateNotEqual(validateScheduleDto.getHourStart(), validateScheduleDto.getHourEnd());
    }

    private void validateAvaliability(ValidateScheduleDto validateScheduleDto, List<ScheduleEntity> scheduleEntityList) {
        for (int i = 0; i < scheduleEntityList.size(); i++) {
            if (validateScheduleDto.getDayOfTheWeek() == scheduleEntityList.get(i).getDayOfTheWeek()) {
                LocalTime newStart = validateScheduleDto.getHourStart();
                LocalTime newEnd = validateScheduleDto.getHourEnd();
                LocalTime existingStart = scheduleEntityList.get(i).getHourStart();
                LocalTime existingEnd = scheduleEntityList.get(i).getHourEnd();
                if (!(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd))) {
                    throw new ScheduleCustomExceptions.ScheduleConflictException();
                }
            }
        }
    }

    private void validateTime(LocalTime start, LocalTime end){
        if (start.isBefore(end)){
            throw new ScheduleCustomExceptions.InvalidTimeException();
        }
    }

    private void validateNotEqual(LocalTime start, LocalTime end){
        if (start.equals(end)){
            throw new ScheduleCustomExceptions.InvalidTimeException();
        }
    }
}