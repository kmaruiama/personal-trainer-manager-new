package com.trainingmanagernew.FinanceModule.Service.PaymentOwnerEntity;

import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterNewPaymentOwnerImpl implements RegisterNewPaymentOwner{

    private final PaymentOwnerEntityRepository paymentOwnerEntityRepository;

    public RegisterNewPaymentOwnerImpl(PaymentOwnerEntityRepository paymentOwnerEntityRepository) {
        this.paymentOwnerEntityRepository = paymentOwnerEntityRepository;
    }

    @Transactional
    @Override
    public void register(UUID userId) {
        PaymentOwnerEntity paymentOwnerEntity = new PaymentOwnerEntity();
        paymentOwnerEntity.setUserId(userId);
        paymentOwnerEntityRepository.save(paymentOwnerEntity);
    }
}
