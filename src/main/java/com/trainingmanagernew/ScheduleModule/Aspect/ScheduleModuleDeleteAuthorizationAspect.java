package com.trainingmanagernew.ScheduleModule.Aspect;

import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
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

@Aspect
@Component
public class ScheduleModuleDeleteAuthorizationAspect {
    private final ScheduleRepository scheduleRepository;
    private final HttpServletRequest httpServletRequest;
    private final ScheduleTokenExtraction scheduleTokenExtraction;

    private UUID idFromResource;
    private UUID idFromToken;

    private Logger LOGGER = Logger.getLogger(ScheduleModuleDeleteAuthorizationAspect.class.getName());

    public ScheduleModuleDeleteAuthorizationAspect(ScheduleRepository scheduleRepository, HttpServletRequest httpServletRequest, ScheduleTokenExtraction scheduleTokenExtraction) {
        this.scheduleRepository = scheduleRepository;
        this.httpServletRequest = httpServletRequest;
        this.scheduleTokenExtraction = scheduleTokenExtraction;
    }

    @Before(value = "@annotation(AuthorizeScheduleModuleDeleteScheduleRequest)")
    public void validateDeleteAuthorization(JoinPoint joinPoint){
        LOGGER.info("INTERCEPTANDO REQUISIÇÃO DE DELETE DO SCHEDULE PARA VALIDAR SUA AUTORIZAÇÃO");
        getArgumentFromJoinPoint(joinPoint);
        idFromToken = getIdFromHeaderToken();
        if(!idFromToken.equals(idFromResource)){
            throw new ScheduleCustomExceptions.UnauthorizedRequest();
        }
    }

    private void getArgumentFromJoinPoint(JoinPoint joinPoint){
        Optional<Object> optionalArgument = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof UUID)
                .findFirst();
        if (optionalArgument.isPresent()){
            Object argument = optionalArgument.get();
            if (argument instanceof UUID uuid){
                processScheduleId(uuid);
            }
            else {
                throw new ScheduleCustomExceptions.NoCompatibleObjectFoundInInterceptedMethodArgument();
            }
        }
        else {
            throw new ScheduleCustomExceptions.NoCompatibleObjectFoundInInterceptedMethodArgument();
        }
    }

    private void processScheduleId(UUID id){
        Optional<ScheduleEntity> scheduleEntityOptional = scheduleRepository.findById(id);
        ScheduleEntity scheduleEntity;
        if (scheduleEntityOptional.isPresent()){
            scheduleEntity = scheduleEntityOptional.get();
        }
        else {
            throw new ScheduleCustomExceptions.ScheduleEntityNotFoundException();
        }
        idFromResource = scheduleEntity.getScheduleOwner().getUserId();
        LOGGER.info("TOKEN INTERCEPTADO PELO PARAMETRO: " + id);
    }

    private UUID getIdFromHeaderToken(){
        String token = httpServletRequest.getHeader("Authorization");
        UUID id = scheduleTokenExtraction.extractUuid(token);
        LOGGER.info("TOKEN INTERCEPTADO PELO REQUEST: " + id);
        return id;
    }
}
