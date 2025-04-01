package com.trainingmanagernew.BodyModule.Service.Get;

import com.trainingmanagernew.BodyModule.Dto.BodyGetDto;

import java.util.List;
import java.util.UUID;

public interface GetAllBodyEntitiesById {
    List<BodyGetDto> get(UUID id);
}
