package com.trainingmanagernew.TrainerModule.Service.Register;

import com.trainingmanagernew.TrainerModule.Dto.TrainerDto;

public interface NewTrainerValidationService {
    void checkCpfUnique(String cpf);
    void validate(TrainerDto trainerDto);
}
