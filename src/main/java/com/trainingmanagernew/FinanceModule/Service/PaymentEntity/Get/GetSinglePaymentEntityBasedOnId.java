package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Get;

import com.trainingmanagernew.FinanceModule.Dto.PaymentEntity.PaymentGetDto;

import java.util.UUID;

public interface GetSinglePaymentEntityBasedOnId {
     PaymentGetDto get(UUID id);
}
