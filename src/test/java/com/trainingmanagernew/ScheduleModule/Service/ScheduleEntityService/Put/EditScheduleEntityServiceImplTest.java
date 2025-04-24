package com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Put;

import com.trainingmanagernew.ScheduleModule.Dto.SchedulePostDto;
import com.trainingmanagernew.ScheduleModule.Dto.ValidateScheduleDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import com.trainingmanagernew.ScheduleModule.Service.ValidateScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditScheduleEntityServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ValidateScheduleService validateScheduleService;

    @InjectMocks
    private EditScheduleEntityServiceImpl editScheduleEntityService;

    @Test
    void shouldGetTheCorrectEntityAndEditIt(){
        UUID randomScheduleId = UUID.randomUUID();
        UUID randomScheduleOwnerId = UUID.randomUUID();

        SchedulePostDto schedulePostDto = new SchedulePostDto();
        schedulePostDto.setScheduleOwnerId(randomScheduleOwnerId);
        schedulePostDto.setOptionalScheduleId(randomScheduleId);
        schedulePostDto.setDayOfTheWeek(1);
        schedulePostDto.setHourEnd(LocalTime.of(10, 20));
        schedulePostDto.setHourEnd(LocalTime.of(10, 50));

        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDayOfTheWeek(5);
        scheduleEntity.setHourStart(LocalTime.of(4, 20));
        scheduleEntity.setHourEnd(LocalTime.of(5, 10));

        when(scheduleRepository.findById(schedulePostDto.getOptionalScheduleId())).thenReturn(Optional.of(scheduleEntity));

        editScheduleEntityService.edit(schedulePostDto);

        verify(validateScheduleService).validate(any(ValidateScheduleDto.class));
        verify(scheduleRepository).save(scheduleEntity);
        assertEquals(1, scheduleEntity.getDayOfTheWeek());
        assertEquals(schedulePostDto.getHourStart(), scheduleEntity.getHourStart());
        assertEquals(schedulePostDto.getHourEnd(), scheduleEntity.getHourEnd());
    }

}