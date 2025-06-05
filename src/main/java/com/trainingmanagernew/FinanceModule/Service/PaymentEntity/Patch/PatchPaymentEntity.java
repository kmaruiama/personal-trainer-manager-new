package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Patch;

import com.trainingmanagernew.FinanceModule.Dto.PaymentEntity.PaymentPostDto;

public interface PatchPaymentEntity {
    void patch (PaymentPostDto paymentPostDto);
}
