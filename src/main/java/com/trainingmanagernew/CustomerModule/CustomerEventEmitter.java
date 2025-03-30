package com.trainingmanagernew.CustomerModule;


import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

public class CustomerEventEmitter {
    private final ApplicationEventPublisher eventPublisher;

    CustomerEventEmitter(ApplicationEventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    public void customerRegistered(UUID customerId){
        eventPublisher.publishEvent(new GenericEvent<>(this, "SUCCESSFUL-CUSTOMER-REGISTRATION", customerId, null));
    };
}
