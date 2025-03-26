package com.trainingmanagernew.UserModule.Service.Register;

public interface NewUserValidation {
    void checkEmailUnique(String email);
    void checkUsernameUnique(String username);
}
