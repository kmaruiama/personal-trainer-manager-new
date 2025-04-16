package com.trainingmanagernew.BodyModule.Service.BodyEntityService.Get;

import com.trainingmanagernew.BodyModule.Dto.Body.BodyGetDto;

import java.util.UUID;

public interface GetLastBodyEntityByOwnerId {
    BodyGetDto get (UUID bodyOwnerId);
}
