package com.trainingmanagernew.UserModule.Aspect;

import com.trainingmanagernew.CustomerModule.Service.LocalJwtExtractor.TokenExtraction;
import com.trainingmanagernew.UserModule.Dto.UserDto;
import com.trainingmanagernew.UserModule.Exception.UserCustomExceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;



@Aspect
@Component
public class AuthorizationAspect {

    private final HttpServletRequest httpServletRequest;
    private final TokenExtraction tokenExtraction;

    AuthorizationAspect(HttpServletRequest httpServletRequest, TokenExtraction tokenExtraction){
        this.httpServletRequest = httpServletRequest;
        this.tokenExtraction = tokenExtraction;
    }

    @Before(value = "@annotation(AuthorizeRequest)")
    public void validateRequestAuthorization(JoinPoint joinPoint){
        UUID interceptedId = getIdFromPointcutArgument(joinPoint);
        UUID tokenId = getIdFromHeaderToken();
        if (!interceptedId.equals(tokenId)){
            throw new UserCustomExceptions.UnauthorizedRequest();
        }
    }

    private UUID getIdFromPointcutArgument(JoinPoint joinPoint){
        Object [] arguments = joinPoint.getArgs();
        UUID id = null;
        Optional<Object> dtoOpt = Arrays.stream(joinPoint.getArgs())
                /*horripilante mas é o único jeito de conseguir interceptar os métodos,
                 conforme for adicionando outros dtos vou aumentando a lista do aspecto
                 com || */
                .filter(arg -> arg instanceof UserDto)
                .findFirst();
        if (dtoOpt.isPresent()) {
            Object dto = dtoOpt.get();
            if (dto instanceof UserDto){
                id = ((UserDto) dto).getId();
            }
            else {
                throw new UserCustomExceptions.NoCompatibleObjectFoundInInterceptedMethodArgument();
            }
        }
        else {
            throw new UserCustomExceptions.NoArgument();
        }
        return id;
    }

    private UUID getIdFromHeaderToken(){
        String token = httpServletRequest.getHeader("Authorization");
        return tokenExtraction.extractUuid(token);
    }

}
