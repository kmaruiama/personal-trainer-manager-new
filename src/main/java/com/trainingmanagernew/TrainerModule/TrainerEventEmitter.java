package com.trainingmanagernew.TrainerModule;

import com.trainingmanagernew.SharedDataTypes.GenericEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TrainerEventEmitter {
    private final ApplicationEventPublisher eventPublisher;

    TrainerEventEmitter(ApplicationEventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    public void unsuccessfulTrainerRegistration(UUID userId){
        eventPublisher.publishEvent(new GenericEvent<>(this, "UNSUCCESSFUL-TRAINER-REGISTRATION", userId, null));
    }
}
