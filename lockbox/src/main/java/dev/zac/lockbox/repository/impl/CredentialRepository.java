package dev.zac.lockbox.repository.impl;

import dev.zac.lockbox.entity.Credential;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class CredentialRepository extends FirestoreRepository<Credential> {

    @Override
    protected String getEntityId(Credential entity) {
        return entity.getId();
    }
    
    public List<Credential> findByCompanyId(String companyId) {
        return findAll().stream()
            .filter(credential -> credential.getCompanyId().equals(companyId))
            .collect(Collectors.toList());
    }
}
