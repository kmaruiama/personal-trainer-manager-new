package com.trainingmanagernew.BodyModule.Service.HeightEntityService.Get;

import com.trainingmanagernew.BodyModule.Dto.Height.HeightGetDto;

import java.util.List;
import java.util.UUID;

public interface GetAllHeightEntitiesById {
    List<HeightGetDto> get(UUID id);
}
