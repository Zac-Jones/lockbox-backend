package dev.zac.lockbox.repository.impl;

import dev.zac.lockbox.entity.TwoFactorAuth;
import org.springframework.stereotype.Repository;

@Repository
public class TwoFactorAuthRepository extends FirestoreRepository<TwoFactorAuth> {

    @Override
    protected String getEntityId(TwoFactorAuth entity) {
        return entity.getId();
    }
    
}
