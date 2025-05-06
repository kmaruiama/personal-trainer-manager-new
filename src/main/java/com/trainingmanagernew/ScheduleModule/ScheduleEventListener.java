package com.trainingmanagernew.ScheduleModule;

import com.trainingmanagernew.ScheduleModule.Service.ScheduleOwnerEntityService.Register.RegisterNewScheduleOwnerService;
import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ScheduleEventListener {
    private final RegisterNewScheduleOwnerService registerNewScheduleOwnerService;

    public ScheduleEventListener(RegisterNewScheduleOwnerService registerNewScheduleOwnerService) {
        this.registerNewScheduleOwnerService = registerNewScheduleOwnerService;
    }

    @EventListener
    public void eventHandler(GenericEvent<?> event){
        switch (event.getEventType()){
            case "USER-REGISTERED-COMPLETE":
                registerNewScheduleOwnerService.register(event.getUserId());
                break;
        }
    }
}
