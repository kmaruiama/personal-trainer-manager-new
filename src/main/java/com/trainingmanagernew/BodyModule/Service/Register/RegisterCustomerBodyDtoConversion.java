package com.trainingmanagernew.BodyModule.Service.Register;

import com.trainingmanagernew.BodyModule.Dto.BodyIdTransferDto;
import com.trainingmanagernew.CustomerModule.Dto.CustomerIdTransferDto;

public interface RegisterCustomerBodyDtoConversion {
    BodyIdTransferDto conversion(CustomerIdTransferDto customerIdTransferDto);
}
