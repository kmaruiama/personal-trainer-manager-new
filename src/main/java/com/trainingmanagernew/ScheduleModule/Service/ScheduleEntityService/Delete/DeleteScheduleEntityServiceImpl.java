package com.trainingmanagernew.ScheduleModule.Service.ScheduleEntityService.Delete;

import com.trainingmanagernew.ScheduleModule.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteScheduleEntityServiceImpl implements DeleteScheduleEntityService{
    private final ScheduleRepository scheduleRepository;

    public DeleteScheduleEntityServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void delete(UUID id) {
        scheduleRepository.deleteById(id);
    }
}
