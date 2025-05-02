package com.trainingmanagernew.BodyModule.Aspect;

import com.trainingmanagernew.BodyModule.Dto.Body.BodyPostDto;
import com.trainingmanagernew.BodyModule.Dto.Height.HeightGetDto;
import com.trainingmanagernew.BodyModule.Dto.Height.HeightPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Entity.HeightEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
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

    private final BodyEntityRepository bodyEntityRepository;
    private final HeightEntityRepository heightEntityRepository;
    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;
    private final BodyTokenExtraction bodyTokenExtraction;
    private final HttpServletRequest httpServletRequest;

    private Logger LOGGER = Logger.getLogger(BodyModuleAuthorizationAspect.class.getName());


    public BodyModuleAuthorizationAspect(BodyOwnerEntityRepository bodyOwnerEntityRepository,
                                         BodyTokenExtraction bodyTokenExtraction,
                                         HttpServletRequest httpServletRequest,
                                         BodyEntityRepository bodyEntityRepository,
                                         HeightEntityRepository heightEntityRepository) {
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
        this.bodyTokenExtraction = bodyTokenExtraction;
        this.httpServletRequest = httpServletRequest;
        this.bodyEntityRepository = bodyEntityRepository;
        this.heightEntityRepository = heightEntityRepository;
    }

    private UUID interceptedUserId;
    private BodyOwnerEntity bodyOwnerEntity;

    @Before(value = "@annotation(com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest)")
    public void validateRequestAuthorization(JoinPoint joinPoint){
         LOGGER.info("INTERCEPTANDO REQUISIÇÃO DE BODY COMPOSITION PARA VALIDAR SUA AUTORIZAÇÃO");
         getIdFromPointcutArgument(joinPoint);
         UUID tokenId = getIdFromHeaderToken();
         if (!interceptedUserId.equals(tokenId)){
            throw new BodyCustomExceptions.UnauthorizedRequest();
        }
    }

    private void getIdFromPointcutArgument(JoinPoint joinPoint){
        Optional<Object> optionalArgument = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof UUID | arg instanceof BodyPostDto | arg instanceof HeightPostDto)
                .findFirst();
        if (optionalArgument.isPresent()){
            Object argument = optionalArgument.get();
            if (argument instanceof BodyPostDto bodyPostDto){
                processBodyPostDto(bodyPostDto);
            }

            if (argument instanceof UUID id){
                //serve apenas para o get, implementar para o delete depois
                setInterceptedBodyOwnerId(id);
            }

            if (argument instanceof HeightPostDto heightPostDto){
                processHeightPostDto(heightPostDto);
            }
        }
        else {
            throw new BodyCustomExceptions.NoCompatibleObjectFoundInInterceptedMethodArgument();
        }
    }

    private void processBodyPostDto(BodyPostDto bodyPostDto){
        setInterceptedBodyOwnerId(bodyPostDto.getBodyOwnerId());
        if (bodyPostDto.getOptionalBodyEntityId() != null){
            bodyPutResourceOwnershipValidation(bodyPostDto.getOptionalBodyEntityId(), bodyOwnerEntity.getId());
        }
    }

    private void processHeightPostDto(HeightPostDto heightPostDto){
        setInterceptedBodyOwnerId(heightPostDto.getBodyOwnerId());
        if (heightPostDto.getOptionalHeightEntityId() != null){
            heightPutResourceOwnershipValidation(heightPostDto.getOptionalHeightEntityId(), bodyOwnerEntity.getId());
        }
    }

    private void bodyPutResourceOwnershipValidation(UUID id, UUID bodyOwnerId){
        BodyEntity bodyEntity;
        Optional<BodyEntity> bodyEntityOptional = bodyEntityRepository.findById(id);
        if (bodyEntityOptional.isPresent()){
            bodyEntity = bodyEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.BodyEntityNotFound();
        }
        if (!bodyEntity.getBodyOwnerEntity().getId().equals(bodyOwnerId)){
            throw new BodyCustomExceptions.UnauthorizedRequest();
        }
    }

    private void heightPutResourceOwnershipValidation(UUID id, UUID bodyOwnerId){
        HeightEntity heightEntity;
        Optional<HeightEntity> heightEntityOptional = heightEntityRepository.findById(id);
        if (heightEntityOptional.isPresent()){
            heightEntity = heightEntityOptional.get();
        }
        else{
            throw new BodyCustomExceptions.HeightEntityNotFound();
        }
        if (!heightEntity.getBodyOwnerEntity().getId().equals(bodyOwnerId)){
            throw new BodyCustomExceptions.UnauthorizedRequest();
        }
    }

    private void setInterceptedBodyOwnerId(UUID id){
        Optional<BodyOwnerEntity> bodyOwnerEntityOptional = bodyOwnerEntityRepository.findById(id);
        if (bodyOwnerEntityOptional.isPresent()){
            bodyOwnerEntity = bodyOwnerEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.BodyOwnerEntityNotFound();
        }
        LOGGER.info("INTERCEPTANDO ID DOS PARAMETROS PARA O SERVIÇO");
        this.interceptedUserId = bodyOwnerEntity.getCustomerOwnerId();
    }

    private UUID getIdFromHeaderToken(){
        String token = httpServletRequest.getHeader("Authorization");
        UUID id = bodyTokenExtraction.extractUuid(token);
        LOGGER.info("TOKEN INTERCEPTADO PELO REQUEST: " + id);
        return id;
    }
}
