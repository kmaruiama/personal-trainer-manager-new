package com.trainingmanagernew.CustomerModule;


import com.trainingmanagernew.CustomerModule.Dto.CustomerIdTransferDto;
import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerEventEmitter {
    private final ApplicationEventPublisher eventPublisher;

    CustomerEventEmitter(ApplicationEventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    public void customerRegistered(CustomerIdTransferDto customerIdTransferDto){
        eventPublisher.publishEvent(new GenericEvent<>(this, "SUCCESSFUL-CUSTOMER-REGISTRATION", null, customerIdTransferDto));
    };
}
