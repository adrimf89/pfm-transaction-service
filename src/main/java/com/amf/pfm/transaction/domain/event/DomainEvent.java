package com.amf.pfm.transaction.domain.event;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime getEventTime();
}
