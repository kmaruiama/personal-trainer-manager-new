package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Delete;

import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletePaymentImpl implements DeletePayment{
    private final PaymentEntityRepository paymentEntityRepository;

    public DeletePaymentImpl(PaymentEntityRepository paymentEntityRepository) {
        this.paymentEntityRepository = paymentEntityRepository;
    }

    @Override
    public void delete(UUID id) {
        paymentEntityRepository.deleteById(id);
    }
}
