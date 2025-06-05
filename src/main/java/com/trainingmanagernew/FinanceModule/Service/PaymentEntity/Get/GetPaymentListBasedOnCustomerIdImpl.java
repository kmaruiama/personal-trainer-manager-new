package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Get;

import com.trainingmanagernew.FinanceModule.Dto.PaymentEntity.PaymentGetListNodeDto;
import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetPaymentListBasedOnCustomerIdImpl implements GetPaymentListBasedOnCustomerId{
    private final PaymentEntityRepository paymentEntityRepository;

    public GetPaymentListBasedOnCustomerIdImpl(PaymentEntityRepository paymentEntityRepository) {
        this.paymentEntityRepository = paymentEntityRepository;
    }

    @Override
    public List<PaymentGetListNodeDto> get(UUID customerId, int page) {
        Pageable pageable = PageRequest.of(page, 15);
        List<PaymentEntity> paymentEntityList = paymentEntityRepository.findByCustomerId(customerId, pageable);
        return convert(paymentEntityList);
    }

    private List<PaymentGetListNodeDto> convert(List<PaymentEntity> paymentEntityList){
        List<PaymentGetListNodeDto> paymentGetListNodeDtoList = new ArrayList<>();
        for (int i = 0; i<paymentEntityList.size(); i++){
            PaymentGetListNodeDto paymentGetListNodeDto = new PaymentGetListNodeDto();
            paymentGetListNodeDto.setId(paymentEntityList.get(i).getId());
            paymentGetListNodeDto.setDueDate(paymentEntityList.get(i).getDueDate());
            paymentGetListNodeDto.setPayed(paymentEntityList.get(i).isPayed());
            paymentGetListNodeDtoList.add(paymentGetListNodeDto);
        }
        return paymentGetListNodeDtoList;
    }
}