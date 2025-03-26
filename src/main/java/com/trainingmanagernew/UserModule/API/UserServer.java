package com.trainingmanagernew.UserModule.API;

import com.trainingmanagernew.UserModule.Dto.UserDto;

import java.util.UUID;

public interface UserServer {
    public UserDto returnFullUser(UUID id);
    public String returnEmail(UUID id);
    public String returnUsername(UUID id);

}
