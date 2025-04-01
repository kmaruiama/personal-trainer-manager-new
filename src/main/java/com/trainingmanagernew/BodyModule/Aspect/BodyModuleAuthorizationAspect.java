package com.trainingmanagernew.BodyModule.Aspect;

import com.trainingmanagernew.BodyModule.Dto.BodyPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import com.trainingmanagernew.BodyModule.Service.LocalJwtExtractor.BodyTokenExtraction;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Component
@Aspect
public class BodyModuleAuthorizationAspect {

    private Logger LOGGER = Logger.getLogger(BodyModuleAuthorizationAspect.class.getName());

    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;
    private final BodyTokenExtraction bodyTokenExtraction;
    private final HttpServletRequest httpServletRequest;

    public BodyModuleAuthorizationAspect(BodyOwnerEntityRepository bodyOwnerEntityRepository,
                                         BodyTokenExtraction bodyTokenExtraction,
                                         HttpServletRequest httpServletRequest) {
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
        this.bodyTokenExtraction = bodyTokenExtraction;
        this.httpServletRequest = httpServletRequest;
    }

    private UUID interceptedId;

    @Before(value = "@annotation(AuthorizeBodyModuleRequest)")
    public void validateRequestAuthorization(JoinPoint joinPoint){
         LOGGER.info("INTERCEPTANDO REQUISIÇÃO DE BODY COMPOSITION PARA VALIDAR SUA AUTORIZAÇÃO");
         getIdFromPointcutArgument(joinPoint);
         UUID tokenId = getIdFromHeaderToken();
         if (!interceptedId.equals(tokenId)){
            throw new BodyCustomExceptions.UnauthorizedRequest();
        }
    }

    private void getIdFromPointcutArgument(JoinPoint joinPoint){
        Optional<Object> optionalArgument = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof UUID | arg instanceof BodyPostDto)
                .findFirst();
        if (optionalArgument.isPresent()){
            Object argument = optionalArgument.get();
            if (argument instanceof BodyPostDto){
                BodyPostDto bodyPostDto = (BodyPostDto) argument;
                returnInterceptedIdFromPostOrPut(bodyPostDto);
            }
            if (argument instanceof UUID){
                UUID id = (UUID) argument;
                setInterceptedId(id);
            }
        }
        else {
            throw new BodyCustomExceptions.NoCompatibleObjectFoundInInterceptedMethodArgument();
        }
    }

    private void returnInterceptedIdFromPostOrPut(BodyPostDto bodyPostDto){
        UUID id = bodyPostDto.getBodyOwnerId();
        setInterceptedId(id);
    }


    private void setInterceptedId(UUID id){
        Optional<BodyOwnerEntity> bodyOwnerEntityOptional = bodyOwnerEntityRepository.findById(id);
        BodyOwnerEntity bodyOwnerEntity;
        if (bodyOwnerEntityOptional.isPresent()){
            bodyOwnerEntity = bodyOwnerEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.BodyOwnerEntityNotFound();
        }
        this.interceptedId = bodyOwnerEntity.getCustomerOwnerId();
    }

    private UUID getIdFromHeaderToken(){
        String token = httpServletRequest.getHeader("Authorization");
        return bodyTokenExtraction.extractUuid(token);
    }
}
