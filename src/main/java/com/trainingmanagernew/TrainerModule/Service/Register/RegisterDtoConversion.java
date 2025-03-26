package com.trainingmanagernew.TrainerModule.Service.Register;

import com.trainingmanagernew.TrainerModule.Dto.TrainerDto;
import com.trainingmanagernew.UserModule.Dto.RegisterDto;

import java.util.UUID;

public interface RegisterDtoConversion {
    TrainerDto convert(RegisterDto registerDto, UUID userId);
}
