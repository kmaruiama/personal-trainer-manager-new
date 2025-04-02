package com.trainingmanagernew.BodyModule.Service.BodyEntityService.Get;

import com.trainingmanagernew.BodyModule.Dto.Body.BodyGetDto;

import java.util.List;
import java.util.UUID;

public interface GetAllBodyEntitiesById {
    List<BodyGetDto> get(UUID id);
}
