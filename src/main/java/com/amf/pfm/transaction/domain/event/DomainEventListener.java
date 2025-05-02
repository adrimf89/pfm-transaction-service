package com.amf.pfm.transaction.domain.event;

public interface DomainEventListener<T extends DomainEvent> {

    default void onEvent(T event) {
        try {
            processEvent(event);
        } catch (Exception e) {
            processError(event, e);
        }
    }

    void processEvent(T event);
    void processError(T event, Exception e);
}
