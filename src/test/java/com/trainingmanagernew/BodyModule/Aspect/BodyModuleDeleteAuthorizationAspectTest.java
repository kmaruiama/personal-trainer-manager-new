package com.trainingmanagernew.BodyModule.Aspect;

import com.trainingmanagernew.BodyModule.Dto.Shared.DeleteResourceDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Entity.HeightEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
import com.trainingmanagernew.BodyModule.Service.LocalJwtExtractor.BodyTokenExtraction;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BodyModuleDeleteAuthorizationAspectTest {
    @Mock private BodyEntityRepository bodyEntityRepository;
    @Mock private HeightEntityRepository heightEntityRepository;
    @Mock private BodyTokenExtraction bodyTokenExtraction;
    @Mock private HttpServletRequest httpServletRequest;
    @InjectMocks private BodyModuleDeleteAuthorizationAspect bodyModuleDeleteAuthorizationAspect;

    @Test
    void deleteBodyRequestThatMustPassBecauseTheExtractedIdFromBodyOwnersOwnerAndTokenExtractedIdAreEqual(){
        DeleteResourceDto deleteResourceDto = new DeleteResourceDto();
        deleteResourceDto.setId(UUID.randomUUID());
        deleteResourceDto.setType("BODY");

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{deleteResourceDto});

        UUID mockedUserId = UUID.randomUUID();
        BodyOwnerEntity bodyOwnerEntity = new BodyOwnerEntity();
        bodyOwnerEntity.setCustomerOwnerId(mockedUserId);

        BodyEntity bodyEntity = new BodyEntity();
        bodyEntity.setBodyOwnerEntity(bodyOwnerEntity);

        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
        when(bodyTokenExtraction.extractUuid(eq("blablabla"))).thenReturn(mockedUserId);
        when(bodyEntityRepository.findById(eq(deleteResourceDto.getId()))).thenReturn(Optional.of(bodyEntity));

        assertDoesNotThrow(() -> {
            bodyModuleDeleteAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });
    }

    @Test
    void deleteHeightRequestThatMustPassBecauseTheExtractedIdFromBodyOwnersOwnerAndTokenExtractedIdAreEqual(){
        DeleteResourceDto deleteResourceDto = new DeleteResourceDto();
        deleteResourceDto.setId(UUID.randomUUID());
        deleteResourceDto.setType("HEIGHT");

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{deleteResourceDto});

        UUID mockedUserId = UUID.randomUUID();
        BodyOwnerEntity bodyOwnerEntity = new BodyOwnerEntity();
        bodyOwnerEntity.setCustomerOwnerId(mockedUserId);

        HeightEntity heightEntity = new HeightEntity();
        heightEntity.setBodyOwnerEntity(bodyOwnerEntity);

        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
        when(bodyTokenExtraction.extractUuid(eq("blablabla"))).thenReturn(mockedUserId);
        when(heightEntityRepository.findById(eq(deleteResourceDto.getId()))).thenReturn(Optional.of(heightEntity));

        assertDoesNotThrow(() -> {
            bodyModuleDeleteAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });
    }

    @Test
    void deleteBodyRequestThatMustNotPassBecauseTheExtractedIdFromBodyOwnersOwnerAndTokenExtractedIdArentEqual(){
        DeleteResourceDto deleteResourceDto = new DeleteResourceDto();
        deleteResourceDto.setId(UUID.randomUUID());
        deleteResourceDto.setType("BODY");

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{deleteResourceDto});

        UUID mockedUserId = UUID.randomUUID();
        BodyOwnerEntity bodyOwnerEntity = new BodyOwnerEntity();
        bodyOwnerEntity.setCustomerOwnerId(mockedUserId);

        BodyEntity bodyEntity = new BodyEntity();
        bodyEntity.setBodyOwnerEntity(bodyOwnerEntity);

        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
                                                                                //diferentes
        when(bodyTokenExtraction.extractUuid(eq("blablabla"))).thenReturn(UUID.randomUUID());
        when(bodyEntityRepository.findById(eq(deleteResourceDto.getId()))).thenReturn(Optional.of(bodyEntity));

        assertThrows(BodyCustomExceptions.UnauthorizedRequest.class,() -> {
            bodyModuleDeleteAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });
    }

    @Test
    void deleteHeightRequestThatMustNotPassBecauseTheExtractedIdFromBodyOwnersOwnerAndTokenExtractedIdArentEqual(){
        DeleteResourceDto deleteResourceDto = new DeleteResourceDto();
        deleteResourceDto.setId(UUID.randomUUID());
        deleteResourceDto.setType("HEIGHT");

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{deleteResourceDto});

        UUID mockedUserId = UUID.randomUUID();
        BodyOwnerEntity bodyOwnerEntity = new BodyOwnerEntity();
        bodyOwnerEntity.setCustomerOwnerId(mockedUserId);

        HeightEntity heightEntity = new HeightEntity();
        heightEntity.setBodyOwnerEntity(bodyOwnerEntity);

        when(httpServletRequest.getHeader("Authorization")).thenReturn("blablabla");
                                                                                //diferentes
        when(bodyTokenExtraction.extractUuid(eq("blablabla"))).thenReturn(UUID.randomUUID());
        when(heightEntityRepository.findById(eq(deleteResourceDto.getId()))).thenReturn(Optional.of(heightEntity));

        assertThrows(BodyCustomExceptions.UnauthorizedRequest.class,() -> {
            bodyModuleDeleteAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });
    }

    @Test
    void deleteRequestThatMustNotPassBecauseTheTypeIsInvalid(){
        DeleteResourceDto deleteResourceDto = new DeleteResourceDto();
        deleteResourceDto.setId(UUID.randomUUID());
        deleteResourceDto.setType("BLABLABLA");

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{deleteResourceDto});

        assertThrows(BodyCustomExceptions.IncorrectResourceType.class, () ->{
            bodyModuleDeleteAuthorizationAspect.validateRequestAuthorization(joinPoint);
        });
    }
}