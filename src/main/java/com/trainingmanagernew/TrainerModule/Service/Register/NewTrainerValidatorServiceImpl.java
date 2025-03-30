package com.trainingmanagernew.TrainerModule.Service.Register;

import com.trainingmanagernew.TrainerModule.Dto.TrainerDto;
import com.trainingmanagernew.TrainerModule.Exception.TrainerCustomExceptions;
import com.trainingmanagernew.TrainerModule.Repository.TrainerRepository;
import com.trainingmanagernew.TrainerModule.TrainerEventEmitter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NewTrainerValidatorServiceImpl implements NewTrainerValidationService {
    private final TrainerRepository trainerRepository;
    private final TrainerEventEmitter trainerEventEmitter;
    private UUID userId = null;

    NewTrainerValidatorServiceImpl(TrainerRepository trainerRepository, TrainerEventEmitter trainerEventEmitter){
        this.trainerRepository = trainerRepository;
        this.trainerEventEmitter = trainerEventEmitter;
    }

    @Override
    public void checkCpfUnique(String cpf) {
        if (trainerRepository.existsByCpf(cpf)) {
            emitUnsuccessfulTrainerRegistration();
            throw new TrainerCustomExceptions.CpfAlreadyExistsException();
        }
    }

    @Override
    public void validate(TrainerDto trainerDto){
        this.userId = trainerDto.getUserId();
        checkCpfUnique(trainerDto.getCpf());
    }

    private void emitUnsuccessfulTrainerRegistration(){
        if (userId != null){
            trainerEventEmitter.unsuccessfulTrainerRegistration(userId);
        }
    }
}
