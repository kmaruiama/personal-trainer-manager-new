package com.trainingmanagernew.FinanceModule;

import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class FinanceEventListener {
    @EventListener
    public void eventHandler(GenericEvent<?> event){
        switch(event.getEventType()){
            case "USER-REGISTERED-COMPLETE":

        }
    }
}
