package dev.zac.lockbox.repository.impl;

import dev.zac.lockbox.entity.SystemLog;
import org.springframework.stereotype.Repository;

@Repository
public class SystemLogRepository extends FirestoreRepository<SystemLog> {

    @Override
    protected String getEntityId(SystemLog entity) {
        return entity.getId();
    }
    
}
