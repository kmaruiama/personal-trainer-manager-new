package com.trainingmanagernew.CustomerModule.Service.Get;

import com.trainingmanagernew.CustomerModule.Dto.CustomerGetDto;

import java.util.List;
import java.util.UUID;

public interface ReturnCustomerService {
    List<CustomerGetDto> returnAllCustomers(String token);
}
