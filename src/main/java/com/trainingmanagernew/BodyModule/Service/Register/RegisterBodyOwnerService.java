package com.trainingmanagernew.BodyModule.Service.Register;

import com.trainingmanagernew.BodyModule.Dto.BodyIdTransferDto;

import java.util.UUID;

public interface RegisterBodyOwnerService {
    void register(BodyIdTransferDto bodyIdTransferDto);
}
