package com.trainingmanagernew.UserModule.Service.Validation;

import com.trainingmanagernew.UserModule.Dto.RegisterDto;

public interface NewUserValidationService {
    void checkEmailUnique(String email);
    void checkUsernameUnique(String username);
    void checkPhoneNumberUnique(String number);
    void validate(RegisterDto registerDto);
}
