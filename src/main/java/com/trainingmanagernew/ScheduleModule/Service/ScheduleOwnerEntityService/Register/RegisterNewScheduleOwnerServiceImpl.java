package com.trainingmanagernew.ScheduleModule.Service.ScheduleOwnerEntityService.Register;

import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import com.trainingmanagernew.ScheduleModule.Repository.ScheduleOwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterNewScheduleOwnerServiceImpl implements RegisterNewScheduleOwnerService {
    private final ScheduleOwnerRepository scheduleOwnerRepository;

    public RegisterNewScheduleOwnerServiceImpl(ScheduleOwnerRepository scheduleOwnerRepository) {
        this.scheduleOwnerRepository = scheduleOwnerRepository;
    }

    @Transactional
    @Override
    public void register(UUID userId) {
        ScheduleOwnerEntity scheduleOwnerEntity = new ScheduleOwnerEntity();
        scheduleOwnerEntity.setUserId(userId);
        scheduleOwnerRepository.save(scheduleOwnerEntity);
    }
}
