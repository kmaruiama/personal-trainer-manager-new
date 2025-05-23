package com.trainingmanagernew.FinanceModule.Service.PaymentPlanEntity.Post;

import com.trainingmanagernew.FinanceModule.Dto.PaymentPlanPostDto;
import com.trainingmanagernew.FinanceModule.Entity.PaymentMethod;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentPlanEntity;
import com.trainingmanagernew.FinanceModule.Exception.FinanceCustomExceptions;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import com.trainingmanagernew.FinanceModule.Repository.PaymentPlanEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddNewPaymentPlanServiceImpl implements AddNewPaymentPlanService {
    private final PaymentOwnerEntityRepository paymentOwnerEntityRepository;
    private final PaymentPlanEntityRepository paymentPlanEntityRepository;

    public AddNewPaymentPlanServiceImpl(PaymentOwnerEntityRepository paymentOwnerEntityRepository, PaymentPlanEntityRepository paymentPlanEntityRepository) {
        this.paymentOwnerEntityRepository = paymentOwnerEntityRepository;
        this.paymentPlanEntityRepository = paymentPlanEntityRepository;
    }

    @Transactional
    @Override
    public void add(PaymentPlanPostDto paymentPlanPostDto) {
        PaymentPlanEntity paymentPlanEntity = new PaymentPlanEntity();
        paymentPlanEntity.setPaymentDay(paymentPlanPostDto.getPaymentDay());
        paymentPlanEntity.setDescription(paymentPlanPostDto.getDescription());
        paymentPlanEntity.setStartDate(paymentPlanPostDto.getStartDate());
        paymentPlanEntity.setRecurringAmount(paymentPlanPostDto.getRecurringAmount());
        setPaymentOwnerEntity(paymentPlanEntity, paymentPlanPostDto.getPaymentOwnerEntityId());
        setPaymentMethod(paymentPlanEntity, paymentPlanPostDto.getPaymentMode());
        if (paymentPlanPostDto.getEndDate() != null){
            paymentPlanEntity.setEndDate(paymentPlanPostDto.getEndDate());
        }
        paymentPlanEntityRepository.save(paymentPlanEntity);
    }

    private void setPaymentOwnerEntity(PaymentPlanEntity paymentPlanEntity, UUID id){
        Optional<PaymentOwnerEntity> paymentOwnerEntityOptional = paymentOwnerEntityRepository.findById(id);
        if (paymentOwnerEntityOptional.isPresent()){
            paymentPlanEntity.setPaymentOwnerEntity(paymentOwnerEntityOptional.get());
        }
        else {
            throw new FinanceCustomExceptions.FinanceOwnerEntityNotFoundException();
        }
    }

    private void setPaymentMethod(PaymentPlanEntity paymentPlanEntity, String paymentMode){
        switch (paymentMode){
            case "DAILY":
                paymentPlanEntity.setPaymentMode(PaymentMethod.DAILY);
                break;
            case "WEEKLY":
                paymentPlanEntity.setPaymentMode(PaymentMethod.WEEKLY);
                break;
            case "MONTHLY":
                paymentPlanEntity.setPaymentMode(PaymentMethod.MONTHLY);
                break;
            case "YEARLY":
                paymentPlanEntity.setPaymentMode(PaymentMethod.YEARLY);
                break;
            case "CUSTOM":
                paymentPlanEntity.setPaymentMode(PaymentMethod.CUSTOM);
                break;
        }
    }
}
