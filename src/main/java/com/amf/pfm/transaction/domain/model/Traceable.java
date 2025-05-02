package com.amf.pfm.transaction.domain.model;

import java.time.LocalDateTime;

public class Traceable {

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public Traceable() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Traceable(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
