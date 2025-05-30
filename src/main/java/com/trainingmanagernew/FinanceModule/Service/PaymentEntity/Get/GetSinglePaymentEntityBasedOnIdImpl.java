package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Get;

import com.trainingmanagernew.FinanceModule.API.Request.FinanceApiRequests;
import com.trainingmanagernew.FinanceModule.Dto.PaymentEntity.PaymentGetDto;
import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Exception.FinanceCustomExceptions;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetSinglePaymentEntityBasedOnIdImpl implements GetSinglePaymentEntityBasedOnId{

    private final PaymentEntityRepository paymentEntityRepository;
    private final FinanceApiRequests financeApiRequests;

    public GetSinglePaymentEntityBasedOnIdImpl(PaymentEntityRepository paymentEntityRepository, FinanceApiRequests financeApiRequests) {
        this.paymentEntityRepository = paymentEntityRepository;
        this.financeApiRequests = financeApiRequests;
    }

    @Override
    public PaymentGetDto get(UUID id) {
        Optional<PaymentEntity> paymentEntityOptional = paymentEntityRepository.findById(id);
        PaymentEntity paymentEntity;
        if (paymentEntityOptional.isPresent()){
            paymentEntity = paymentEntityOptional.get();
        }
        else {
            throw new FinanceCustomExceptions.FinanceEntityNotFoundException();
        }
        PaymentGetDto paymentGetDto = new PaymentGetDto();
        insertExternalInformation(paymentGetDto, paymentEntity.getCustomerId());
        convertEntityToDto(paymentGetDto, paymentEntity);
        return paymentGetDto;
    }

    private void insertExternalInformation (PaymentGetDto paymentGetDto, UUID customerId){
        String customerName = financeApiRequests.getCustomerName(customerId);
        paymentGetDto.setCustomerName(customerName);
    }

    private void convertEntityToDto (PaymentGetDto paymentGetDto, PaymentEntity paymentEntity){
        paymentGetDto.setAmount(paymentEntity.getAmount());
        paymentGetDto.setId(paymentEntity.getId());
        paymentGetDto.setDueDate(paymentEntity.getDueDate());
        paymentGetDto.setPayed(paymentGetDto.isPayed());
        if (paymentEntity.getPaymentOwnerEntity()!= null) {
            paymentGetDto.setPaymentPlanEntityId(paymentEntity.getPaymentOwnerEntity().getId());
        }
        if (paymentEntity.getPaymentDate() != null) {
            paymentGetDto.setPaymentDate(paymentEntity.getPaymentDate());
        }
        if(paymentEntity.getDescription() != null) {
            paymentGetDto.setDescription(paymentGetDto.getDescription());
        }
    }
}
