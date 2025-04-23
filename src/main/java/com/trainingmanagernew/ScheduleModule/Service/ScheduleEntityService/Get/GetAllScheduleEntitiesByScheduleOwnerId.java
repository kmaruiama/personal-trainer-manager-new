package com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Get;

import com.trainingmanagernew.ScheduleModule.Dto.ScheduleGetDto;

import java.util.List;
import java.util.UUID;

public interface GetAllScheduleEntitiesByScheduleOwnerId {
    List<ScheduleGetDto> get(UUID id);
}
