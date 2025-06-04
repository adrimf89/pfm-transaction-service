package com.amf.pfm.transaction.domain.model;

import java.time.LocalDateTime;

public class Traceable {

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;

    public Traceable() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deletedAt = null;
    }

    public Traceable(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() { return deletedAt; }

    protected void updated() {
        this.updatedAt = LocalDateTime.now();
    }

    protected void deleted() {
        this.deletedAt = LocalDateTime.now();
    }
}
