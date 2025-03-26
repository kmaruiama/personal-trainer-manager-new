package com.trainingmanagernew.TrainerModule.Service.Delete;

import com.trainingmanagernew.TrainerModule.Repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteTrainerService {
    private final TrainerRepository trainerRepository;

    public DeleteTrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public void delete(UUID id){
        trainerRepository.deleteByOwnerId(id);
    }
}
