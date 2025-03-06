package dev.zac.lockbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.zac.lockbox.entity.Credential;
import dev.zac.lockbox.repository.impl.CredentialRepository;

@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;
    
    public Credential createCredential(Credential credential) {
        credentialRepository.save(credential);
        return credential;
    }

    public Credential updateCredential(Credential credential) {
        credentialRepository.update(credential);
        return credential;
    }

    public void deleteCredential(String id) {
        credentialRepository.delete(id);
    }

    public Credential getCredentialById(String id) throws Exception {
        return credentialRepository.findById(id);
    }

    public List<Credential> getAllCredentials() {
        return credentialRepository.findAll();
    }
}
