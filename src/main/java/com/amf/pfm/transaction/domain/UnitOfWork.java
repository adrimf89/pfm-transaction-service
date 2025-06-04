package com.amf.pfm.transaction.domain;

public interface UnitOfWork {
    void transactional(Runnable action);
}
