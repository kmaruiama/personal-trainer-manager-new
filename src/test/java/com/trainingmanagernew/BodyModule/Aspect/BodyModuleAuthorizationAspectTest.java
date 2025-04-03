package com.trainingmanagernew.BodyModule.Aspect;

import com.trainingmanagernew.BodyModule.Dto.Body.BodyPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
import com.trainingmanagernew.BodyModule.Service.LocalJwtExtractor.BodyTokenExtraction;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BodyModuleAuthorizationAspectTest {

    @Mock private BodyEntityRepository bodyEntityRepository;
    @Mock private HeightEntityRepository heightEntityRepository;
    @Mock private BodyOwnerEntityRepository bodyOwnerEntityRepository;
    @Mock private BodyTokenExtraction bodyTokenExtraction;
    @Mock private HttpServletRequest httpServletRequest;

    @InjectMocks BodyModuleAuthorizationAspect bodyModuleAuthorizationAspect;


    @Test
    void postRequestThatMustPassBecauseBothCustomerOwnerIdAndTokenExtractedIdAreEqual(){
        BodyPostDto bodyPostDto = new BodyPostDto();
        bodyPostDto.setBodyFat(20);
        bodyPostDto.setWeight(75);
        bodyPostDto.setDate(LocalDate.of(2025, 4, 1));
        bodyPostDto.setBodyOwnerId(UUID.randomUUID());

        UUID fakeId = UUID.randomUUID();

        JoinPoint joinPoint = mock(JoinPoint.class);

        when(joinPoint.getArgs()).thenReturn(new Object[]{bodyPostDto});

        BodyOwnerEntity mockBodyOwnerEntity = mock(BodyOwnerEntity.class);
        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");

        when(mockBodyOwnerEntity.getCustomerOwnerId()).thenReturn(fakeId);
        when(bodyOwnerEntityRepository.findById(bodyPostDto.getBodyOwnerId())).thenReturn(Optional.of(mockBodyOwnerEntity));
        when(bodyTokenExtraction.extractUuid("blablabla")).thenReturn(fakeId);

        assertDoesNotThrow(() -> bodyModuleAuthorizationAspect.validateRequestAuthorization(joinPoint));

        verify(bodyOwnerEntityRepository).findById(bodyPostDto.getBodyOwnerId());
        verify(bodyTokenExtraction).extractUuid(any(String.class));
    }

    @Test
    void postRequestThatMustNotPassBecauseBothCustomerOwnerIdAndTokenExtractedIdArentEqual(){
        BodyPostDto bodyPostDto = new BodyPostDto();
        bodyPostDto.setBodyFat(20);
        bodyPostDto.setWeight(75);
        bodyPostDto.setDate(LocalDate.of(2025, 4, 1));
        bodyPostDto.setBodyOwnerId(UUID.randomUUID());

        UUID fakeId = UUID.randomUUID();
        UUID fakeIdTwo = UUID.randomUUID();

        JoinPoint joinPoint = mock(JoinPoint.class);

        when(joinPoint.getArgs()).thenReturn(new Object[]{bodyPostDto});

        BodyOwnerEntity mockBodyOwnerEntity = mock(BodyOwnerEntity.class);
        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");

        when(mockBodyOwnerEntity.getCustomerOwnerId()).thenReturn(fakeId);
        when(bodyOwnerEntityRepository.findById(bodyPostDto.getBodyOwnerId())).thenReturn(Optional.of(mockBodyOwnerEntity));
        when(bodyTokenExtraction.extractUuid("blablabla")).thenReturn(fakeIdTwo);

        assertThrows(BodyCustomExceptions.UnauthorizedRequest.class, () -> {
            bodyModuleAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });

        verify(bodyOwnerEntityRepository).findById(bodyPostDto.getBodyOwnerId());
        verify(bodyTokenExtraction).extractUuid(any(String.class));
    }

    @Test
    void putRequestThatMustPassBecause_BothCustomerOwnerIdAndTokenExtractedIdAreEqual_AND_BodyOwnerIdInResourceAndBodyOwnerIdInRequestAreEqual(){
        BodyPostDto bodyPostDto = new BodyPostDto();
        bodyPostDto.setBodyFat(20);
        bodyPostDto.setWeight(75);
        bodyPostDto.setDate(LocalDate.of(2025, 4, 1));
        bodyPostDto.setBodyOwnerId(UUID.randomUUID());
        bodyPostDto.setOptionalBodyEntityId(UUID.randomUUID());

        UUID fakeBodyOwnerId = UUID.randomUUID();
        UUID fakeUserId = UUID.randomUUID();

        BodyOwnerEntity mockBodyOwnerEntityThatOwnsBodyEntity = mock(BodyOwnerEntity.class);
        when(mockBodyOwnerEntityThatOwnsBodyEntity.getId()).thenReturn(fakeBodyOwnerId);

        BodyEntity mockBodyEntity = mock(BodyEntity.class);
        when(mockBodyEntity.getBodyOwnerEntity()).thenReturn(mockBodyOwnerEntityThatOwnsBodyEntity);

        BodyOwnerEntity mockBodyOwnerEntity = mock(BodyOwnerEntity.class);
        when(mockBodyOwnerEntity.getId()).thenReturn(fakeBodyOwnerId);
        when(mockBodyOwnerEntity.getCustomerOwnerId()).thenReturn(fakeUserId);

        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
        when(bodyTokenExtraction.extractUuid("blablabla")).thenReturn(fakeUserId);

        when(bodyEntityRepository.findById(bodyPostDto.getOptionalBodyEntityId())).thenReturn(Optional.of(mockBodyEntity));
        when(bodyOwnerEntityRepository.findById(bodyPostDto.getBodyOwnerId())).thenReturn(Optional.of(mockBodyOwnerEntity));

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{bodyPostDto});

        assertDoesNotThrow(() -> bodyModuleAuthorizationAspect.validateRequestAuthorization(joinPoint));

        verify(bodyOwnerEntityRepository).findById(bodyPostDto.getBodyOwnerId());
        verify(bodyTokenExtraction).extractUuid(any(String.class));
        verify(bodyEntityRepository).findById(bodyPostDto.getOptionalBodyEntityId());

    }

    @Test
    void putRequestThatMustNotPassBecause_BothCustomerOwnerIdAndTokenExtractedIdAreEqual_AND_BodyOwnerIdInResourceAndBodyOwnerIdInRequestArentEqual(){
        BodyPostDto bodyPostDto = new BodyPostDto();
        bodyPostDto.setBodyFat(20);
        bodyPostDto.setWeight(75);
        bodyPostDto.setDate(LocalDate.of(2025, 4, 1));
        bodyPostDto.setBodyOwnerId(UUID.randomUUID());
        bodyPostDto.setOptionalBodyEntityId(UUID.randomUUID());

        UUID fakeBodyOwnerId = UUID.randomUUID();
        UUID fakeBodyOwnerId2 = UUID.randomUUID();
        UUID fakeUserId = UUID.randomUUID();

        BodyOwnerEntity mockBodyOwnerEntityThatOwnsBodyEntity = mock(BodyOwnerEntity.class);
        when(mockBodyOwnerEntityThatOwnsBodyEntity.getId()).thenReturn(fakeBodyOwnerId2);

        BodyEntity mockBodyEntity = mock(BodyEntity.class);
        when(mockBodyEntity.getBodyOwnerEntity()).thenReturn(mockBodyOwnerEntityThatOwnsBodyEntity);

        BodyOwnerEntity mockBodyOwnerEntity = mock(BodyOwnerEntity.class);
        when(mockBodyOwnerEntity.getId()).thenReturn(fakeBodyOwnerId);
        when(mockBodyOwnerEntity.getCustomerOwnerId()).thenReturn(fakeUserId);

        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
        when(bodyTokenExtraction.extractUuid("blablabla")).thenReturn(fakeUserId);

        when(bodyEntityRepository.findById(bodyPostDto.getOptionalBodyEntityId())).thenReturn(Optional.of(mockBodyEntity));
        when(bodyOwnerEntityRepository.findById(bodyPostDto.getBodyOwnerId())).thenReturn(Optional.of(mockBodyOwnerEntity));

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{bodyPostDto});

        assertThrows(BodyCustomExceptions.UnauthorizedRequest.class, () -> {
            bodyModuleAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });

        verify(bodyOwnerEntityRepository).findById(bodyPostDto.getBodyOwnerId());
        verify(bodyEntityRepository).findById(bodyPostDto.getOptionalBodyEntityId());
        verifyNoInteractions(bodyTokenExtraction);
    }

    @Test
    void getRequestThatMustPassBecauseBothCustomerOwnerIdAndTokenExtractedIdAreEqual(){
        UUID id = UUID.randomUUID();
        BodyOwnerEntity mockBodyOwnerEntity = mock(BodyOwnerEntity.class);

        JoinPoint joinPoint = mock(JoinPoint.class);

        when(joinPoint.getArgs()).thenReturn(new Object[]{id});

        when(mockBodyOwnerEntity.getCustomerOwnerId()).thenReturn(id);
        when(bodyOwnerEntityRepository.findById(id)).thenReturn(Optional.of(mockBodyOwnerEntity));


        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
        when(bodyTokenExtraction.extractUuid("blablabla")).thenReturn(id);

        assertDoesNotThrow(() -> bodyModuleAuthorizationAspect.validateRequestAuthorization(joinPoint));

        verifyNoInteractions(bodyEntityRepository);
        verify(bodyOwnerEntityRepository).findById(any(UUID.class));
        verify(bodyTokenExtraction).extractUuid(any(String.class));
    }

    @Test
    void getRequestThatMustNotPassBecauseBothCustomerOwnerIdAndTokenExtractedIdArentEqual(){
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        BodyOwnerEntity mockBodyOwnerEntity = mock(BodyOwnerEntity.class);

        JoinPoint joinPoint = mock(JoinPoint.class);

        when(joinPoint.getArgs()).thenReturn(new Object[]{id});

        when(mockBodyOwnerEntity.getCustomerOwnerId()).thenReturn(id);
        when(bodyOwnerEntityRepository.findById(id)).thenReturn(Optional.of(mockBodyOwnerEntity));


        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
        when(bodyTokenExtraction.extractUuid("blablabla")).thenReturn(id2);

        assertThrows(BodyCustomExceptions.UnauthorizedRequest.class, () -> {
            bodyModuleAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });

        verifyNoInteractions(bodyEntityRepository);
        verify(bodyOwnerEntityRepository).findById(any(UUID.class));
        verify(bodyTokenExtraction).extractUuid(any(String.class));
    }
}