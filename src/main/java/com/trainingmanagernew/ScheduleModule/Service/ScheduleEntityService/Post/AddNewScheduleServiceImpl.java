package com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Post;

import com.trainingmanagernew.ScheduleModule.Dto.SchedulePostDto;
import com.trainingmanagernew.ScheduleModule.Dto.ValidateScheduleDto;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleOwnerRepository;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import com.trainingmanagernew.ScheduleModule.Service.InitializeScheduleEntityOwner;
import com.trainingmanagernew.ScheduleModule.Service.ValidateScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AddNewScheduleServiceImpl implements AddNewScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ValidateScheduleService validateScheduleService;
    private final InitializeScheduleEntityOwner initializeScheduleEntityOwner;

    public AddNewScheduleServiceImpl(ScheduleRepository scheduleRepository,
                                     ValidateScheduleService validateScheduleService,
                                     InitializeScheduleEntityOwner initializeScheduleEntityOwner){
        this.scheduleRepository = scheduleRepository;
        this.validateScheduleService = validateScheduleService;
        this.initializeScheduleEntityOwner = initializeScheduleEntityOwner;
    }

    @Transactional
    @Override
    public void add(SchedulePostDto schedulePostDto){
        validate(schedulePostDto);
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        ScheduleOwnerEntity scheduleOwnerEntity = initializeScheduleEntityOwner.initialize(schedulePostDto.getScheduleOwnerId());
        scheduleEntity.setScheduleOwner(scheduleOwnerEntity);
        scheduleEntity.setHourStart(schedulePostDto.getHourStart());
        scheduleEntity.setHourEnd(schedulePostDto.getHourEnd());
        scheduleEntity.setDayOfTheWeek(scheduleEntity.getDayOfTheWeek());
        scheduleRepository.save(scheduleEntity);
    }

    private void validate(SchedulePostDto schedulePostDto){
        ValidateScheduleDto validateScheduleDto = new ValidateScheduleDto();
        validateScheduleDto.setDayOfTheWeek(schedulePostDto.getDayOfTheWeek());
        validateScheduleDto.setHourStart(schedulePostDto.getHourStart());
        validateScheduleDto.setHourEnd(schedulePostDto.getHourEnd());
        validateScheduleService.validate(validateScheduleDto);
    }

}
