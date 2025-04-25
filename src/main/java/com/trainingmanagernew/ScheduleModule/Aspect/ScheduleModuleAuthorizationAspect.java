package com.trainingmanagernew.ScheduleModule.Aspect;

import com.trainingmanagernew.ScheduleModule.Dto.SchedulePostDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleOwnerRepository;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
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
    Logger LOGGER = Logger.getLogger(ScheduleModuleAuthorizationAspect.class.getName());

    private UUID userFromToken;
    private UUID userFromScheduleOwner;

    public ScheduleModuleAuthorizationAspect(ScheduleOwnerRepository scheduleOwnerRepository, ScheduleRepository scheduleRepository) {
        this.scheduleOwnerRepository = scheduleOwnerRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Before(value = "@annotation(AuthorizeScheduleModuleRequest)")
    public void validateRequestAuthorization(JoinPoint joinPoint){
        LOGGER.info("INTERCEPTANDO REQUISIÇÃO DE SCHEDULE PARA VALIDAR SUA AUTORIZAÇÃO");
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
        userFromScheduleOwner = scheduleOwnerEntity.getUserId();
    }
}
