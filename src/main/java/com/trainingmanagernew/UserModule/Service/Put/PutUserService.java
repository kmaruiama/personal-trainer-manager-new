package com.trainingmanagernew.UserModule.Service.Put;

import com.trainingmanagernew.UserModule.Dto.UserDto;

public interface PutUserService {
    void put(UserDto userDto, String authHeader);
}
