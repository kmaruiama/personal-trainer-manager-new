package com.trainingmanagernew.TrainerModule.Service.Register;

import com.trainingmanagernew.TrainerModule.Dto.TrainerDto;
import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterDtoConversor implements RegisterDtoConversion {
    @Override
    public TrainerDto convert(RegisterDto registerDto, UUID userId) {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setUserId(userId);
        trainerDto.setAddress(registerDto.getAddress());
        trainerDto.setBirth(registerDto.getBirth());
        trainerDto.setName(registerDto.getName());
        trainerDto.setCpf(registerDto.getCpf());
        return trainerDto;
    }
}
