package com.trainingmanagernew.TrainerModule.Service.Register;

import com.trainingmanagernew.TrainerModule.Dto.TrainerDto;
import com.trainingmanagernew.TrainerModule.Entity.TrainerEntity;
import com.trainingmanagernew.TrainerModule.Repository.TrainerRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewTrainerService {

    private final TrainerRepository trainerRepository;
    private final NewTrainerValidatorService newTrainerValidatorService;

    RegisterNewTrainerService(TrainerRepository trainerRepository, NewTrainerValidatorService newTrainerValidatorService){
        this.trainerRepository = trainerRepository;
        this.newTrainerValidatorService = newTrainerValidatorService;
    }

    public void register(TrainerDto trainerDto){
        newTrainerValidatorService.validate(trainerDto);
        TrainerEntity trainerEntity = new TrainerEntity();
        trainerEntity.setAddress(trainerDto.getAddress());
        trainerEntity.setCpf(trainerDto.getCpf());
        trainerEntity.setName(trainerDto.getName());
        trainerEntity.setBirthDate(trainerDto.getBirth());
        trainerEntity.setOwnerId(trainerDto.getUserId());
        trainerRepository.save(trainerEntity);
    }
}
