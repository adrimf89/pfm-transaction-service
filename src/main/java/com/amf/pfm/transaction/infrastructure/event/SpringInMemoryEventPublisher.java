package com.amf.pfm.transaction.infrastructure.event;

import com.amf.pfm.transaction.domain.event.DomainEvent;
import com.amf.pfm.transaction.domain.event.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringInMemoryEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringInMemoryEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
}
