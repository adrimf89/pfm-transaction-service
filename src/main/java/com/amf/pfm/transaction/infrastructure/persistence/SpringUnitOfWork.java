package com.amf.pfm.transaction.infrastructure.persistence;

import com.amf.pfm.transaction.domain.UnitOfWork;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class SpringUnitOfWork implements UnitOfWork {

    @Override
    @Transactional
    public void transactional(Runnable action) {
        action.run();
    }
}
