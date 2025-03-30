package com.trainingmanagernew.UserModule.Service.Get;

import com.trainingmanagernew.UserModule.Dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserServiceImpl implements GetUserService{
    @Override
    public UserDto get(String authHeader){
        return new UserDto();
    }
}
