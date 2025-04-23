package com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Get;

import com.trainingmanagernew.ScheduleModule.Dto.ScheduleGetDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import com.trainingmanagernew.ScheduleModule.Service.InitializeScheduleEntityOwner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetAllScheduleEntitiesByScheduleOwnerIdImpl implements GetAllScheduleEntitiesByScheduleOwnerId{
    private final InitializeScheduleEntityOwner initializeScheduleEntityOwner;
    private final ScheduleRepository scheduleRepository;

    public GetAllScheduleEntitiesByScheduleOwnerIdImpl(InitializeScheduleEntityOwner initializeScheduleEntityOwner, ScheduleRepository scheduleRepository) {
        this.initializeScheduleEntityOwner = initializeScheduleEntityOwner;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<ScheduleGetDto> get(UUID id) {
        ScheduleOwnerEntity scheduleOwnerEntity = initializeScheduleEntityOwner.initialize(id);
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.findAllByScheduleOwnerEntity(scheduleOwnerEntity);
        if (scheduleEntityList.isEmpty()){
            throw new ScheduleCustomExceptions.ScheduleEntityListIsEmptyException();
        }
        return convert(scheduleEntityList);
    }

    private List<ScheduleGetDto> convert(List<ScheduleEntity> scheduleEntityList){
        List<ScheduleGetDto> scheduleGetDtoList = new ArrayList<>();
        for (int i = 0; i<scheduleEntityList.size(); i++){
            ScheduleGetDto scheduleGetDto = new ScheduleGetDto();
            scheduleGetDto.setScheduleId(scheduleEntityList.get(i).getId());
            scheduleGetDto.setHourEnd(scheduleEntityList.get(i).getHourEnd());
            scheduleGetDto.setHourStart(scheduleEntityList.get(i).getHourStart());
            scheduleGetDto.setDayOfTheWeek(scheduleEntityList.get(i).getDayOfTheWeek());
            scheduleGetDtoList.add(scheduleGetDto);
        }
        return scheduleGetDtoList;
    }
}
