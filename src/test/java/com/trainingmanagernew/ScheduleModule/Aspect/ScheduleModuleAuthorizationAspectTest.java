package com.trainingmanagernew.ScheduleModule.Aspect;

import com.trainingmanagernew.ScheduleModule.Dto.SchedulePostDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleOwnerRepository;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import com.trainingmanagernew.ScheduleModule.Service.LocalJwtExtractor.ScheduleTokenExtraction;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ScheduleModuleAuthorizationAspectTest {
    @Mock private ScheduleOwnerRepository scheduleOwnerRepository;
    @Mock private ScheduleRepository scheduleRepository;
    @Mock private ScheduleTokenExtraction scheduleTokenExtraction;
    @Mock private HttpServletRequest httpServletRequest;
    @InjectMocks ScheduleModuleAuthorizationAspect scheduleModuleAuthorizationAspect;

    @Test
    void postRequestThatMustPassBecauseUserIdExtractedFromScheduleOwnerEntityAndTokenExtractedIdAreEqual(){
        SchedulePostDto schedulePostDto = new SchedulePostDto();
        schedulePostDto.setScheduleOwnerId(UUID.randomUUID());
        schedulePostDto.setHourEnd(LocalTime.of(5, 30));
        schedulePostDto.setHourStart(LocalTime.of(6, 30));
        schedulePostDto.setDayOfTheWeek(3);

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{schedulePostDto});

        UUID idOne = UUID.randomUUID();
        ScheduleOwnerEntity scheduleOwnerEntity = new ScheduleOwnerEntity();
        scheduleOwnerEntity.setUserId(idOne);
        scheduleOwnerEntity.setScheduleEntityList(new ArrayList<>());
        scheduleOwnerEntity.setId(UUID.randomUUID());

        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
        when(scheduleOwnerRepository.findById(any(UUID.class))).thenReturn(Optional.of(scheduleOwnerEntity));
        when(scheduleTokenExtraction.extractUuid(any(String.class))).thenReturn(idOne);

        scheduleModuleAuthorizationAspect.validateRequestAuthorization(joinPoint);

        verify(scheduleOwnerRepository).findById(any(UUID.class));
        verify(scheduleTokenExtraction).extractUuid(any(String.class));
    }

    @Test
    void postRequestThatMustNotPassBecauseUserIdExtractedFromScheduleOwnerEntityAndTokenExtractedIdArentEqual(){
        SchedulePostDto schedulePostDto = new SchedulePostDto();
        schedulePostDto.setScheduleOwnerId(UUID.randomUUID());
        schedulePostDto.setHourEnd(LocalTime.of(5, 30));
        schedulePostDto.setHourStart(LocalTime.of(6, 30));
        schedulePostDto.setDayOfTheWeek(3);

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{schedulePostDto});

        UUID idOne = UUID.randomUUID();
        UUID idTwo = UUID.randomUUID();
        ScheduleOwnerEntity scheduleOwnerEntity = new ScheduleOwnerEntity();
        scheduleOwnerEntity.setUserId(idOne);
        scheduleOwnerEntity.setScheduleEntityList(new ArrayList<>());
        scheduleOwnerEntity.setId(UUID.randomUUID());

        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
        when(scheduleOwnerRepository.findById(any(UUID.class))).thenReturn(Optional.of(scheduleOwnerEntity));
        when(scheduleTokenExtraction.extractUuid(any(String.class))).thenReturn(idTwo);

        assertThrows(ScheduleCustomExceptions.UnauthorizedRequest.class, () -> {
            scheduleModuleAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });
        verify(scheduleOwnerRepository).findById(any(UUID.class));
        verify(scheduleTokenExtraction).extractUuid(any(String.class));
    }
}