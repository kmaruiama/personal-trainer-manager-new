package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Get;

import com.trainingmanagernew.FinanceModule.Dto.PaymentEntity.PaymentGetListNodeDto;

import java.util.List;
import java.util.UUID;

public interface GetPaymentListBasedOnCustomerId {
    List<PaymentGetListNodeDto> get(UUID customerId, int page);
}
