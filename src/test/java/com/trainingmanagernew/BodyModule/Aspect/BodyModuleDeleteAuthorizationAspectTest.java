package com.trainingmanagernew.BodyModule.Aspect;

import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
import com.trainingmanagernew.BodyModule.Service.LocalJwtExtractor.BodyTokenExtraction;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BodyModuleDeleteAuthorizationAspectTest {
    @Mock private BodyEntityRepository bodyEntityRepository;
    @Mock private HeightEntityRepository heightEntityRepository;
    @Mock private BodyTokenExtraction bodyTokenExtraction;
    @Mock private HttpServletRequest httpServletRequest;
    @InjectMocks private BodyModuleDeleteAuthorizationAspect bodyModuleDeleteAuthorizationAspect;

    @Test
    void deleteRequestThatMustPassBecauseTheExtractedIdFromBodyOwnersOwnerAndTokenExtractedIdAreEqual(){

    }
}