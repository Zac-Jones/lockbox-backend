package dev.zac.lockbox.repository.impl;

import dev.zac.lockbox.entity.Company;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository extends FirestoreRepository<Company> {

    @Override
    protected String getEntityId(Company entity) {
        return entity.getId();
    }
    
}
