package com.trainingmanagernew.TrainerModule.Service.Register;

import com.trainingmanagernew.TrainerModule.Dto.TrainerDto;
import com.trainingmanagernew.TrainerModule.Entity.TrainerEntity;
import com.trainingmanagernew.TrainerModule.Repository.TrainerRepository;
import com.trainingmanagernew.TrainerModule.TrainerEventEmitter;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewTrainerService {

    private final TrainerRepository trainerRepository;
    private final NewTrainerValidationService newTrainerValidationService;
    private final TrainerEventEmitter trainerEventEmitter;

    RegisterNewTrainerService(TrainerRepository trainerRepository, NewTrainerValidationService newTrainerValidatorService, TrainerEventEmitter trainerEventEmitter){
        this.trainerRepository = trainerRepository;
        this.newTrainerValidationService = newTrainerValidatorService;
        this.trainerEventEmitter = trainerEventEmitter;
    }

    public void register(TrainerDto trainerDto){
        newTrainerValidationService.validate(trainerDto);
        TrainerEntity trainerEntity = new TrainerEntity();
        trainerEntity.setAddress(trainerDto.getAddress());
        trainerEntity.setCpf(trainerDto.getCpf());
        trainerEntity.setName(trainerDto.getName());
        trainerEntity.setBirthDate(trainerDto.getBirth());
        trainerEntity.setOwnerId(trainerDto.getUserId());
        trainerRepository.save(trainerEntity);
        trainerEventEmitter.successfulTrainerRegistration(trainerDto.getUserId());
    }
}
