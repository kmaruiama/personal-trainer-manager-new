package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Patch;

import com.trainingmanagernew.FinanceModule.Dto.PaymentEntity.PaymentPostDto;
import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Exception.FinanceCustomExceptions;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatchPaymentEntityImpl implements PatchPaymentEntity{
    private final PaymentEntityRepository paymentEntityRepository;

    public PatchPaymentEntityImpl(PaymentEntityRepository paymentEntityRepository) {
        this.paymentEntityRepository = paymentEntityRepository;
    }

    @Transactional
    @Override
    public void patch(PaymentPostDto paymentPostDto) {
        Optional<PaymentEntity> paymentEntityOptional = paymentEntityRepository.findById(paymentPostDto.getOptionalPaymentEntityId());
        PaymentEntity paymentEntity;
        if (paymentEntityOptional.isPresent()){
            paymentEntity = paymentEntityOptional.get();
        }
        else {
            throw new FinanceCustomExceptions.FinanceEntityNotFoundException();
        }
        editFields(paymentPostDto, paymentEntity);
    }

    private void editFields(PaymentPostDto paymentPostDto, PaymentEntity paymentEntity){
        paymentEntity.setDescription(paymentPostDto.getDescription());
        paymentEntity.setDueDate(paymentPostDto.getDueDate());
        paymentEntity.setAmount(paymentPostDto.getAmount());
        paymentEntity.setPayed(paymentPostDto.isPayed());
        paymentEntity.setPaymentDate(paymentPostDto.getPaymentDate());

       /*

        --ATRIBUTOS QUE NAO FAZ SENTIDO EDITAR!--
        paymentEntity.setPaymentOwnerEntity();
        paymentEntity.setPaymentPlanEntity();
        paymentEntity.setCustomerId();

       */
    }
}
