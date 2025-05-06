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
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Aspect
@Component
public class BodyModuleDeleteAuthorizationAspect{

    private final BodyEntityRepository bodyEntityRepository;
    private final HeightEntityRepository heightEntityRepository;
    private final BodyTokenExtraction bodyTokenExtraction;
    Logger LOGGER = Logger.getLogger(BodyModuleDeleteAuthorizationAspect.class.getName());
    private final HttpServletRequest httpServletRequest;

    private UUID idFromResource;
    private UUID idFromToken;

    public BodyModuleDeleteAuthorizationAspect(BodyEntityRepository bodyEntityRepository,
                                               HeightEntityRepository heightEntityRepository,
                                               HttpServletRequest httpServletRequest, BodyTokenExtraction bodyTokenExtraction) {
        this.bodyEntityRepository = bodyEntityRepository;
        this.heightEntityRepository = heightEntityRepository;
        this.httpServletRequest = httpServletRequest;
        this.bodyTokenExtraction = bodyTokenExtraction;
    }

    @Before(value = "@annotation(AuthorizeBodyResourceRequest)")
    public void validateRequestAuthorization(JoinPoint joinPoint){
        LOGGER.info("VALIDANDO A AUTORIZAÇÃO DO DELETE DA COMPOSIÇÃO CORPORAL");
        DeleteResourceDto deleteResourceDto = getArgumentFromJoinPoint(joinPoint);
        if (deleteResourceDto.getType().equals("BODY")){
            processBodyDeletion(deleteResourceDto.getId());
        }
        else if (deleteResourceDto.getType().equals("HEIGHT")){
            processHeightDeletion(deleteResourceDto.getId());
        }
        else {
            throw new BodyCustomExceptions.IncorrectResourceType();
        }

        idFromToken = getIdFromHeaderToken();

        if (!idFromResource.equals(idFromToken)){
            throw new BodyCustomExceptions.UnauthorizedRequest();
        }
    }

    private DeleteResourceDto getArgumentFromJoinPoint(JoinPoint joinPoint){
        Optional<Object> optionalArgument = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof DeleteResourceDto)
                .findFirst();
        DeleteResourceDto deleteResourceDto;
        if (optionalArgument.isPresent() && optionalArgument.get() instanceof DeleteResourceDto){
            deleteResourceDto = (DeleteResourceDto) optionalArgument.get();
        }
        else throw new BodyCustomExceptions.NoCompatibleObjectFoundInInterceptedMethodArgument();
        return deleteResourceDto;
    }

    private void processBodyDeletion(UUID id){
        Optional<BodyEntity> bodyEntityOptional = bodyEntityRepository.findById(id);
        BodyEntity bodyEntity;
        if (bodyEntityOptional.isPresent()){
            bodyEntity = bodyEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.BodyEntityNotFound();
        }
        BodyOwnerEntity bodyOwnerEntity = bodyEntity.getBodyOwnerEntity();
        idFromResource = bodyOwnerEntity.getCustomerOwnerId();
    }

    private void processHeightDeletion(UUID id){
        Optional<HeightEntity> heightEntityOptional = heightEntityRepository.findById(id);
        HeightEntity heightEntity;
        if (heightEntityOptional.isPresent()){
            heightEntity = heightEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.HeightEntityNotFound();
        }
        BodyOwnerEntity bodyOwnerEntity = heightEntity.getBodyOwnerEntity();
        idFromResource = bodyOwnerEntity.getCustomerOwnerId();
    }

    private UUID getIdFromHeaderToken(){
        String token = httpServletRequest.getHeader("Authorization");
        UUID id = bodyTokenExtraction.extractUuid(token);
        LOGGER.info("TOKEN INTERCEPTADO PELO REQUEST: " + id);
        return id;
    }
}
