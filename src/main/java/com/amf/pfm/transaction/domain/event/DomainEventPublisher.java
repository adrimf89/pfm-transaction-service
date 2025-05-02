package com.amf.pfm.transaction.domain.event;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
