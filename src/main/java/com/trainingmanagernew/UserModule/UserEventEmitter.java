package com.trainingmanagernew.UserModule;

import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserEventEmitter {
    private final ApplicationEventPublisher eventPublisher;

    UserEventEmitter(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void newUserRegistered(UUID userId, RegisterDto registerDto) {
        eventPublisher.publishEvent(new GenericEvent<>(this, "USER-REGISTERED-TRANSIENT", userId, registerDto));
    }

    public void userDeleted(UUID userId){
        eventPublisher.publishEvent(new GenericEvent<>(this, "OBLITERATE", userId, null));
    }

    public void sucessfulRegistration(UUID userId){
        eventPublisher.publishEvent(new GenericEvent<>(this, "USER-REGISTERED-COMPLETE", userId, null));
    }
}

