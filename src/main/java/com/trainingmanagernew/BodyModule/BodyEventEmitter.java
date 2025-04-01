package com.trainingmanagernew.BodyModule;

import org.springframework.context.ApplicationEventPublisher;

public class BodyEventEmitter {
    private final ApplicationEventPublisher eventPublisher;

    BodyEventEmitter(ApplicationEventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }


}
