package com.trainingmanagernew.UserModule.Service.Get;

import com.trainingmanagernew.UserModule.Dto.UserDto;

import java.util.UUID;

public interface GetUserService{
    public UserDto get(String authHeader);
}
