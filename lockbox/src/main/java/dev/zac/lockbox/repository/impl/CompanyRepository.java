package dev.zac.lockbox.repository.impl;

import dev.zac.lockbox.entity.Company;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository extends FirestoreRepository<Company> {

    @Override
    protected String getEntityId(Company entity) {
        return entity.getId();
    }
    
    public List<Company> findByUserId(String userId) {
        return findAll().stream()
            .filter(company -> company.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
}
