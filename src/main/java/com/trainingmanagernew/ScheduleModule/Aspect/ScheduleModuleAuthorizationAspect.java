package com.trainingmanagernew.ScheduleModule.Aspect;

import com.trainingmanagernew.ScheduleModule.Dto.SchedulePostDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleOwnerRepository;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import com.trainingmanagernew.ScheduleModule.Service.LocalJwtExtractor.ScheduleTokenExtraction;
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
public class ScheduleModuleAuthorizationAspect {
    private final ScheduleOwnerRepository scheduleOwnerRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleTokenExtraction scheduleTokenExtraction;
    private final HttpServletRequest httpServletRequest;
    Logger LOGGER = Logger.getLogger(ScheduleModuleAuthorizationAspect.class.getName());

    private UUID userFromToken;
    private UUID userFromScheduleOwner;

    public ScheduleModuleAuthorizationAspect(ScheduleOwnerRepository scheduleOwnerRepository,
                                             ScheduleRepository scheduleRepository,
                                             ScheduleTokenExtraction scheduleTokenExtraction,
                                             HttpServletRequest httpServletRequest) {
        this.scheduleOwnerRepository = scheduleOwnerRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleTokenExtraction = scheduleTokenExtraction;
        this.httpServletRequest = httpServletRequest;
    }

    @Before(value = "@annotation(AuthorizeScheduleModuleRequest)")
    public void validateRequestAuthorization(JoinPoint joinPoint){
        LOGGER.info("INTERCEPTANDO REQUISIÇÃO DE SCHEDULE PARA VALIDAR SUA AUTORIZAÇÃO");
        getArgumentFromJoinPoint(joinPoint);
        userFromToken = getIdFromHeaderToken();
        if(!userFromToken.equals(userFromScheduleOwner)){
            throw new ScheduleCustomExceptions.UnauthorizedRequest();
        }
    }

    private void getArgumentFromJoinPoint(JoinPoint joinPoint){
        Optional<Object> optionalArgument = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof UUID | arg instanceof SchedulePostDto)
                .findFirst();
        if (optionalArgument.isPresent()){
            Object argument = optionalArgument.get();
            if (argument instanceof SchedulePostDto schedulePostDto){
                processSchedulePostDto(schedulePostDto);
            }
            if (argument instanceof UUID id){
                getUserIdFromScheduleOwner(id);
            }
        }
    }

    private void processSchedulePostDto(SchedulePostDto schedulePostDto){
        getUserIdFromScheduleOwner(schedulePostDto.getScheduleOwnerId());
        if (schedulePostDto.getOptionalScheduleId() != null){
            processPutRequisition(schedulePostDto.getOptionalScheduleId(), schedulePostDto.getScheduleOwnerId());
        }
    }

    private void processPutRequisition(UUID scheduleId, UUID scheduleOwnerId){
        Optional<ScheduleEntity> scheduleEntityOptional = scheduleRepository.findById(scheduleId);
        ScheduleEntity scheduleEntity;
        if (scheduleEntityOptional.isPresent()){
            scheduleEntity = scheduleEntityOptional.get();
        }
        else {
            throw new ScheduleCustomExceptions.ScheduleEntityNotFoundException();
        }
        if (!scheduleEntity.getScheduleOwner().getId().equals(scheduleOwnerId)){
            throw new ScheduleCustomExceptions.UnauthorizedRequest();
        }
    }

    private void getUserIdFromScheduleOwner(UUID id){
        Optional<ScheduleOwnerEntity> scheduleOwnerEntityOptional = scheduleOwnerRepository.findById(id);
        ScheduleOwnerEntity scheduleOwnerEntity;
        if (scheduleOwnerEntityOptional.isPresent()){
            scheduleOwnerEntity = scheduleOwnerEntityOptional.get();
        }
        else {
            throw new ScheduleCustomExceptions.ScheduleOwnerNotFoundException();
        }
        LOGGER.info("INTERCEPTANDO ID DOS PARAMETROS PARA O SERVIÇO");
        userFromScheduleOwner = scheduleOwnerEntity.getUserId();
    }

    private UUID getIdFromHeaderToken(){
        String token = httpServletRequest.getHeader("Authorization");
        UUID id = scheduleTokenExtraction.extractUuid(token);
        LOGGER.info("TOKEN INTERCEPTADO PELO REQUEST: " + id);
        return id;
    }
}
