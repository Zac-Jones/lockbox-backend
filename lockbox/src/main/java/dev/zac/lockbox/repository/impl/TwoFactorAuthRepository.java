package dev.zac.lockbox.repository.impl;

import dev.zac.lockbox.entity.TwoFactorAuth;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TwoFactorAuthRepository extends FirestoreRepository<TwoFactorAuth> {

    @Override
    protected String getEntityId(TwoFactorAuth entity) {
        return entity.getId();
    }
    
    public List<TwoFactorAuth> findByCompanyId(String companyId) {
        return findAll().stream()
            .filter(twoFactorAuth -> twoFactorAuth.getCompanyId().equals(companyId))
            .collect(Collectors.toList());
    }
}
