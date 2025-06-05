package com.trainingmanagernew.FinanceModule.Aspect;

import com.trainingmanagernew.FinanceModule.Dto.PaymentEntity.PaymentPostDto;
import com.trainingmanagernew.FinanceModule.Dto.PaymentPlanPostDto;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Exception.FinanceCustomExceptions;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import com.trainingmanagernew.FinanceModule.Service.LocalJwtExtractor.FinanceTokenExtraction;
import com.trainingmanagernew.FinanceModule.Service.LocalJwtExtractor.FinanceTokenExtractionImpl;
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
public class FinanceModuleAuthorizationAspect {
    private final PaymentOwnerEntityRepository paymentOwnerEntityRepository;
    private final HttpServletRequest httpServletRequest;
    private final FinanceTokenExtractionImpl financeTokenExtractionImpl;
    private final FinanceTokenExtraction financeTokenExtraction;

    Logger LOGGER = Logger.getLogger(FinanceModuleAuthorizationAspect.class.getName());

    private UUID interceptedUserIdFromToken;
    private UUID interceptedUserIdFromDto;

    public FinanceModuleAuthorizationAspect(PaymentOwnerEntityRepository paymentOwnerEntityRepository,
                                            HttpServletRequest httpServletRequest, FinanceTokenExtractionImpl financeTokenExtractionImpl, FinanceTokenExtraction financeTokenExtraction)
    {
        this.paymentOwnerEntityRepository = paymentOwnerEntityRepository;
        this.httpServletRequest = httpServletRequest;
        this.financeTokenExtractionImpl = financeTokenExtractionImpl;
        this.financeTokenExtraction = financeTokenExtraction;
    }

    @Before(value = "@annotation(AuthorizeFinanceModuleRequest)")
    public void validateRequestAuthorization(JoinPoint joinPoint) {
        LOGGER.info("INTERCEPTANDO REQUISIÇÃO DE PAYMENTPLAN/PAYMENT PARA VALIDAR SUA AUTORIZAÇÃO");
        getIdFromPointcutArgument(joinPoint);
        getIdFromHeaderToken();
        if(!interceptedUserIdFromToken.equals(interceptedUserIdFromDto)){
            throw new FinanceCustomExceptions.UnauthorizedRequest();
        }
    }

    private void getIdFromPointcutArgument(JoinPoint joinPoint) {
        Optional<Object> optionalArgument = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof PaymentPlanPostDto | arg instanceof PaymentPostDto)
                .findFirst();
        if (optionalArgument.isPresent()) {
            Object argument = optionalArgument.get();
            if (argument instanceof PaymentPlanPostDto paymentPlanPostDto) {
                processPaymentPlan(paymentPlanPostDto);
            }
            if (argument instanceof PaymentPostDto paymentPostDto) {
                processPayment(paymentPostDto);
            }
        }
        else {
            throw new FinanceCustomExceptions.NoCompatibleObjectFoundInInterceptedMethodArgument();
        }
    }

    //deve ser possivel criar uma bruxaria com generals pra pegar a mesma variavel de classes diferentes
    //mas como nao a conheco vou manter 2 metodos pra ficar facil de ver
    private void  processPaymentPlan(PaymentPlanPostDto paymentPlanPostDto){
        Optional<PaymentOwnerEntity> paymentOwnerEntityOptional = paymentOwnerEntityRepository.findById(paymentPlanPostDto.getPaymentOwnerEntityId());
        if (paymentOwnerEntityOptional.isPresent()){
            interceptedUserIdFromDto = paymentOwnerEntityOptional.get().getUserId();
            LOGGER.info("TOKEN INTERCEPTADO PELO DTO" + interceptedUserIdFromDto);
        }
        else {
            throw new FinanceCustomExceptions.FinanceOwnerEntityNotFoundException();
        }
    }

    private void processPayment(PaymentPostDto paymentPostDto){
        Optional<PaymentOwnerEntity> paymentOwnerEntityOptional = paymentOwnerEntityRepository.findById(paymentPostDto.getPaymentOwnerEntityId());
        if (paymentOwnerEntityOptional.isPresent()){
            interceptedUserIdFromDto = paymentOwnerEntityOptional.get().getUserId();
            LOGGER.info("TOKEN INTERCEPTADO PELO DTO" + interceptedUserIdFromDto);
        }
        else {
            throw new FinanceCustomExceptions.FinanceOwnerEntityNotFoundException();
        }
    }

    private void getIdFromHeaderToken(){
        String token = httpServletRequest.getHeader("Authorization");
        UUID id = financeTokenExtraction.extractUuid(token);
        LOGGER.info("TOKEN INTERCEPTADO PELO REQUEST: " + id);
        interceptedUserIdFromToken = id;
    }
}
