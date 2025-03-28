package com.trainingmanagernew.Shared.DataTypes;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class GenericEvent<T> extends ApplicationEvent {
    private final String eventType;
    private final UUID userId;
    private final T eventData;

    public GenericEvent(Object source, String eventType, UUID userId, T eventData) {
        super(source);
        this.eventType = eventType;
        this.userId = userId;
        this.eventData = eventData;
    }

    public String getEventType() {
        return eventType;
    }

    public UUID getUserId() {
        return userId;
    }

    public T getEventData() {
        return eventData;
    }
}

