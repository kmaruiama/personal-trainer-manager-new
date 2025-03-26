package com.trainingmanagernew.TrainerModule;
import com.trainingmanagernew.TrainerModule.Dto.TrainerDto;
import com.trainingmanagernew.TrainerModule.Service.Delete.DeleteTrainerService;
import com.trainingmanagernew.TrainerModule.Service.Register.RegisterDtoConversor;
import com.trainingmanagernew.TrainerModule.Service.Register.RegisterNewTrainerService;
import com.trainingmanagernew.SharedDataTypes.GenericEvent;
import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TrainerEventListener {

    private final RegisterNewTrainerService registerNewTrainerService;
    private final RegisterDtoConversor registerDtoConversor;
    private final DeleteTrainerService deleteTrainerService;

    TrainerEventListener(RegisterNewTrainerService registerNewTrainerService, RegisterDtoConversor registerDtoConversor, DeleteTrainerService deleteTrainerService){
        this.registerNewTrainerService = registerNewTrainerService;
        this.registerDtoConversor = registerDtoConversor;
        this.deleteTrainerService = deleteTrainerService;
    }

    @EventListener
    public void eventHandler(GenericEvent<?> event){
        switch (event.getEventType()){
            case "USER-REGISTERED":
                RegisterDto registerDto = (RegisterDto) event.getEventData();
                TrainerDto trainerDto = registerDtoConversor.convert(registerDto, event.getUserId());
                /*A partir desse ponto, nao fico mais dependente de qualquer
                  tipo de dado vindo de outro módulo */
                registerNewTrainerService.register(trainerDto);
                break;

            case "OBLITERATE":
                deleteTrainerService.delete(event.getUserId());
                break;
        }
    }
}
