package com.trainingmanagernew.ScheduleModule.Service;

import com.trainingmanagernew.ScheduleModule.Dto.ValidateScheduleDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateScheduleServiceImplTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ValidateScheduleServiceImpl validateScheduleService;


                                // scenarios
    //upper bound = | start already_in | start a | end already_in | end a
    //lower bound = | start a | start already_in | end a | end already_in
    //insider = | start already_in | start a | end a | end already_in
    //outsider = | start a | start already_in | end already_in | start a
    @Test
    void mustNotPassBecauseThereIsALowerBoundConflictAndTheHourIsValid(){
        List<ScheduleEntity> mockedSchedules = new ArrayList<>();
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDayOfTheWeek(4);
        scheduleEntity.setHourStart(LocalTime.of(10, 10));
        scheduleEntity.setHourEnd(LocalTime.of(11, 10));
        mockedSchedules.add(scheduleEntity);

        UUID mockedId = UUID.randomUUID();

        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setDayOfTheWeek(4);
        validateScheduleDto.setHourStart(LocalTime.of(10, 20));
        validateScheduleDto.setHourEnd(LocalTime.of(11, 40));
        validateScheduleDto.setOwnerId(mockedId);

        when(scheduleRepository.findByScheduleOwnerEntity_Id(mockedId)).thenReturn(mockedSchedules);

        assertThrows(ScheduleCustomExceptions.ScheduleConflictException.class, () -> {
            validateScheduleService.validate(validateScheduleDto);
        });
    }

    @Test
    void mustNotPassBecauseThereIsAUpperBoundConflictAndTheHourIsValid(){
        List<ScheduleEntity> mockedSchedules = new ArrayList<>();
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDayOfTheWeek(4);
        scheduleEntity.setHourStart(LocalTime.of(10, 10));
        scheduleEntity.setHourEnd(LocalTime.of(11, 10));
        mockedSchedules.add(scheduleEntity);

        UUID mockedId = UUID.randomUUID();

        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setDayOfTheWeek(4);
        validateScheduleDto.setHourStart(LocalTime.of(9, 20));
        validateScheduleDto.setHourEnd(LocalTime.of(10, 40));
        validateScheduleDto.setOwnerId(mockedId);

        when(scheduleRepository.findByScheduleOwnerEntity_Id(mockedId)).thenReturn(mockedSchedules);

        assertThrows(ScheduleCustomExceptions.ScheduleConflictException.class, () -> {
            validateScheduleService.validate(validateScheduleDto);
        });
    }

    @Test
    void mustNotPassBecauseThereIsAInsiderConflictAndTheHourIsValid(){
        List<ScheduleEntity> mockedSchedules = new ArrayList<>();
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDayOfTheWeek(4);
        scheduleEntity.setHourStart(LocalTime.of(10, 20));
        scheduleEntity.setHourEnd(LocalTime.of(11, 40));
        mockedSchedules.add(scheduleEntity);

        UUID mockedId = UUID.randomUUID();

        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setDayOfTheWeek(4);
        validateScheduleDto.setHourStart(LocalTime.of(10, 21));
        validateScheduleDto.setHourEnd(LocalTime.of(11, 39));
        validateScheduleDto.setOwnerId(mockedId);

        when(scheduleRepository.findByScheduleOwnerEntity_Id(mockedId)).thenReturn(mockedSchedules);

        assertThrows(ScheduleCustomExceptions.ScheduleConflictException.class, () -> {
            validateScheduleService.validate(validateScheduleDto);
        });
    }

    @Test
    void mustNotPassBecauseThereIsAOutsiderConflictAndTheHourIsValid(){
        List<ScheduleEntity> mockedSchedules = new ArrayList<>();
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDayOfTheWeek(4);
        scheduleEntity.setHourStart(LocalTime.of(10, 20));
        scheduleEntity.setHourEnd(LocalTime.of(11, 40));
        mockedSchedules.add(scheduleEntity);

        UUID mockedId = UUID.randomUUID();

        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setDayOfTheWeek(4);
        validateScheduleDto.setHourStart(LocalTime.of(10, 19));
        validateScheduleDto.setHourEnd(LocalTime.of(11, 41));
        validateScheduleDto.setOwnerId(mockedId);

        when(scheduleRepository.findByScheduleOwnerEntity_Id(mockedId)).thenReturn(mockedSchedules);

        assertThrows(ScheduleCustomExceptions.ScheduleConflictException.class, () -> {
            validateScheduleService.validate(validateScheduleDto);
        });
    }

    @Test
    void mustNotPassBecauseTheEndTimeComesBeforeTheStartTime(){
        List<ScheduleEntity> mockedSchedules = new ArrayList<>();
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDayOfTheWeek(4);
        scheduleEntity.setHourStart(LocalTime.of(10, 20));
        scheduleEntity.setHourEnd(LocalTime.of(11, 40));
        mockedSchedules.add(scheduleEntity);

        UUID mockedId = UUID.randomUUID();

        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setDayOfTheWeek(4);
        validateScheduleDto.setHourStart(LocalTime.of(12, 41));
        validateScheduleDto.setHourEnd(LocalTime.of(11, 41));
        validateScheduleDto.setOwnerId(mockedId);

        when(scheduleRepository.findByScheduleOwnerEntity_Id(mockedId)).thenReturn(mockedSchedules);

        assertThrows(ScheduleCustomExceptions.InvalidTimeException.class, () -> {
            validateScheduleService.validate(validateScheduleDto);
        });
    }

    @Test
    void mustNotPassBecauseTheStartAndEndTimesAreEqual(){
        List<ScheduleEntity> mockedSchedules = new ArrayList<>();
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDayOfTheWeek(4);
        scheduleEntity.setHourStart(LocalTime.of(10, 20));
        scheduleEntity.setHourEnd(LocalTime.of(11, 40));
        mockedSchedules.add(scheduleEntity);

        UUID mockedId = UUID.randomUUID();

        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setDayOfTheWeek(4);
        validateScheduleDto.setHourStart(LocalTime.of(11, 41));
        validateScheduleDto.setHourEnd(LocalTime.of(11, 41));
        validateScheduleDto.setOwnerId(mockedId);

        when(scheduleRepository.findByScheduleOwnerEntity_Id(mockedId)).thenReturn(mockedSchedules);

        assertThrows(ScheduleCustomExceptions.InvalidTimeException.class, () -> {
            validateScheduleService.validate(validateScheduleDto);
        });
    }

    @Test
    void mustPass(){
        List<ScheduleEntity> mockedSchedules = new ArrayList<>();
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDayOfTheWeek(4);
        scheduleEntity.setHourStart(LocalTime.of(10, 20));
        scheduleEntity.setHourEnd(LocalTime.of(11, 40));
        mockedSchedules.add(scheduleEntity);

        UUID mockedId = UUID.randomUUID();

        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setDayOfTheWeek(4);
        validateScheduleDto.setHourStart(LocalTime.of(11, 41));
        validateScheduleDto.setHourEnd(LocalTime.of(12, 41));
        validateScheduleDto.setOwnerId(mockedId);

        when(scheduleRepository.findByScheduleOwnerEntity_Id(mockedId)).thenReturn(mockedSchedules);

        validateScheduleService.validate(validateScheduleDto);

        verify(scheduleRepository).findByScheduleOwnerEntity_Id(mockedId);
    }


}