package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Post;

import com.trainingmanagernew.FinanceModule.API.Request.FinanceApiRequests;
import com.trainingmanagernew.FinanceModule.Dto.PaymentPostDto;
import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Exception.FinanceCustomExceptions;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddNewPaymentImpl implements AddNewPayment{

    private final FinanceApiRequests financeApiRequests;
    private final PaymentOwnerEntityRepository paymentOwnerEntityRepository;
    private final PaymentEntityRepository paymentEntityRepository;

    public AddNewPaymentImpl(FinanceApiRequests financeApiRequests, PaymentOwnerEntityRepository paymentOwnerEntityRepository, PaymentEntityRepository paymentEntityRepository) {
        this.financeApiRequests = financeApiRequests;
        this.paymentOwnerEntityRepository = paymentOwnerEntityRepository;
        this.paymentEntityRepository = paymentEntityRepository;
    }

    @Transactional
    @Override
    public void add(PaymentPostDto paymentPostDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCustomerId(paymentPostDto.getCustomerId());
        paymentEntity.setCustomerName(financeApiRequests.getCustomerName(paymentPostDto.getCustomerId()));
        setPaymentOwnerEntity(paymentEntity, paymentPostDto.getPaymentOwnerEntityId());
        setPaymentSchedule(paymentEntity, paymentPostDto);
        paymentEntity.setPayment(paymentPostDto.getPayment());
        paymentEntityRepository.save(paymentEntity);
    }

    private void setPaymentOwnerEntity(PaymentEntity paymentEntity, UUID paymentOwnerEntityId){
        Optional<PaymentOwnerEntity> paymentOwnerEntityOptional = paymentOwnerEntityRepository.findById(paymentOwnerEntityId);
        if (paymentOwnerEntityOptional.isPresent()){
            paymentEntity.setPaymentOwnerEntity(paymentOwnerEntityOptional.get());
        }
        else {
            throw new FinanceCustomExceptions.FinanceOwnerEntityNotFoundException();
        }
    }

    private void setPaymentSchedule(PaymentEntity paymentEntity, PaymentPostDto paymentPostDto){
        paymentEntity.setDatePayment(paymentPostDto.getDatePayment());

        switch (paymentPostDto.getModalidade()){
            case "MONTHLY":
                paymentEntity.setDueDate(paymentPostDto.getDatePayment().plusMonths(1));
                break;
            case "YEARLY":
                paymentEntity.setDueDate(paymentPostDto.getDatePayment().plusYears(1));
                break;
            case "CUSTOM":
                paymentEntity.setDueDate(paymentPostDto.getDueDate());
                break;
        }
    }

}
