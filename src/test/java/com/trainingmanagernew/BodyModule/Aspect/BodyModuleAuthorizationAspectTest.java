package com.trainingmanagernew.BodyModule.Aspect;

import com.trainingmanagernew.BodyModule.Dto.Body.BodyPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

        //no exception must be thrown
        assertDoesNotThrow(() -> bodyModuleAuthorizationAspect.validateRequestAuthorization(joinPoint));

        verify(bodyOwnerEntityRepository).findById(bodyPostDto.getBodyOwnerId());
        verify(bodyTokenExtraction).extractUuid(any(String.class));
    }
}