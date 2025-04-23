package com.trainingmanagernew.ScheduleModule.Service;

import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import com.trainingmanagernew.ScheduleModule.Exception.ScheduleCustomExceptions;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleOwnerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class InitializeScheduleEntityOwner {
    private final ScheduleOwnerRepository scheduleOwnerRepository;

    public InitializeScheduleEntityOwner(ScheduleOwnerRepository scheduleOwnerRepository) {
        this.scheduleOwnerRepository = scheduleOwnerRepository;
    }

    public ScheduleOwnerEntity initialize(UUID id){
        Optional<ScheduleOwnerEntity> scheduleOwnerOptional = scheduleOwnerRepository.findById(id);
        if (scheduleOwnerOptional.isPresent()){
            return scheduleOwnerOptional.get();
        }
        else{
            throw new ScheduleCustomExceptions.ScheduleOwnerNotFoundException();
        }
    }
}
