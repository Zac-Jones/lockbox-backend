package dev.zac.lockbox.repository.impl;

import dev.zac.lockbox.entity.Credential;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialRepository extends FirestoreRepository<Credential> {

    @Override
    protected String getEntityId(Credential entity) {
        return entity.getId();
    }
    
}
