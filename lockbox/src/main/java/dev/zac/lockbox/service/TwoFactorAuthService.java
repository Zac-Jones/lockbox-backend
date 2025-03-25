package dev.zac.lockbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.zac.lockbox.entity.TwoFactorAuth;
import dev.zac.lockbox.repository.impl.TwoFactorAuthRepository;

@Service
public class TwoFactorAuthService {
    
    @Autowired
    private TwoFactorAuthRepository twoFactorAuthRepository;
    
    public TwoFactorAuth createTwoFactorAuth(TwoFactorAuth twoFactorAuth) {
        twoFactorAuthRepository.save(twoFactorAuth);
        return twoFactorAuth;
    }

    public TwoFactorAuth updateTwoFactorAuth(TwoFactorAuth twoFactorAuth) {
        twoFactorAuthRepository.update(twoFactorAuth);
        return twoFactorAuth;
    }

    public void deleteTwoFactorAuth(String id) {
        twoFactorAuthRepository.delete(id);
    }

    public TwoFactorAuth getTwoFactorAuthById(String id) throws Exception {
        return twoFactorAuthRepository.findById(id);
    }

    public List<TwoFactorAuth> getAllTwoFactorAuths() {
        return twoFactorAuthRepository.findAll();
    }

    public List<TwoFactorAuth> getTwoFactorAuthsByCompanyId(String companyId) {
        return twoFactorAuthRepository.findByCompanyId(companyId);
    }
}
