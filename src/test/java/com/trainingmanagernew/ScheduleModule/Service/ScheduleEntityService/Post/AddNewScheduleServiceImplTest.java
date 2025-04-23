package com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Post;

import com.trainingmanagernew.ScheduleModule.Dto.SchedulePostDto;
import com.trainingmanagernew.ScheduleModule.Dto.ValidateScheduleDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import com.trainingmanagernew.ScheduleModule.Service.InitializeScheduleEntityOwner;
import com.trainingmanagernew.ScheduleModule.Service.ValidateScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddNewScheduleServiceImplTest {
    @Mock private ScheduleRepository scheduleRepository;

    @Mock private ValidateScheduleService validateScheduleService;

    @Mock private InitializeScheduleEntityOwner initializeScheduleEntityOwner;

    @InjectMocks private AddNewScheduleServiceImpl addNewScheduleServiceImpl;

    @Test
    void shouldValidateAndSaveSchedule(){
        SchedulePostDto schedulePostDto = new SchedulePostDto();
        schedulePostDto.setHourStart(LocalTime.of(10, 20));
        schedulePostDto.setHourEnd(LocalTime.of(10, 40));
        schedulePostDto.setDayOfTheWeek(5);
        schedulePostDto.setScheduleOwnerId(UUID.randomUUID());

        addNewScheduleServiceImpl.add(schedulePostDto);

        verify(validateScheduleService).validate(any(ValidateScheduleDto.class));
        verify(scheduleRepository).save(any(ScheduleEntity.class));
    }

}