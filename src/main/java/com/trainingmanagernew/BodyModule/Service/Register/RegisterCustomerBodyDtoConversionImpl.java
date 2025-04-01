package com.trainingmanagernew.BodyModule.Service.Register;

import com.trainingmanagernew.BodyModule.Dto.BodyIdTransferDto;
import com.trainingmanagernew.CustomerModule.Dto.CustomerIdTransferDto;
import org.springframework.stereotype.Component;

@Component
public class RegisterCustomerBodyDtoConversionImpl implements RegisterCustomerBodyDtoConversion{
    @Override
    public BodyIdTransferDto conversion(CustomerIdTransferDto customerIdTransferDto) {
        BodyIdTransferDto bodyIdTransferDto = new BodyIdTransferDto();
        bodyIdTransferDto.setCustomerId(customerIdTransferDto.getCustomerId());
        bodyIdTransferDto.setCustomerOwnerId(customerIdTransferDto.getCustomerOwnerId());
        return bodyIdTransferDto;
    }
}
