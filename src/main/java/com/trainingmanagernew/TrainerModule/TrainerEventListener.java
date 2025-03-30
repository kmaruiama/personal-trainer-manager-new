package com.trainingmanagernew.TrainerModule;
import com.trainingmanagernew.TrainerModule.Dto.TrainerDto;
import com.trainingmanagernew.TrainerModule.Service.Delete.DeleteTrainerService;
import com.trainingmanagernew.TrainerModule.Service.Register.RegisterDtoConversion;
import com.trainingmanagernew.TrainerModule.Service.Register.RegisterDtoConversionImpl;
import com.trainingmanagernew.TrainerModule.Service.Register.RegisterNewTrainerService;
import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TrainerEventListener {

    private final RegisterNewTrainerService registerNewTrainerService;
    private final DeleteTrainerService deleteTrainerService;
    private final RegisterDtoConversion registerDtoConversion;

    TrainerEventListener(RegisterNewTrainerService registerNewTrainerService, DeleteTrainerService deleteTrainerService, RegisterDtoConversion registerDtoConversion){
        this.registerNewTrainerService = registerNewTrainerService;
        this.deleteTrainerService = deleteTrainerService;
        this.registerDtoConversion = registerDtoConversion;
    }

    @EventListener
    public void eventHandler(GenericEvent<?> event){
        switch (event.getEventType()){
            case "USER-REGISTERED-TRANSIENT":
                RegisterDto registerDto = (RegisterDto) event.getEventData();
                TrainerDto trainerDto = registerDtoConversion.convert(registerDto, event.getUserId());
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
