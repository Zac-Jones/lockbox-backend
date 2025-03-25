package dev.zac.lockbox.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import dev.zac.lockbox.entity.Credential;
import dev.zac.lockbox.service.CompanyService;
import dev.zac.lockbox.service.CredentialService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CredentialController {
    @Autowired
    CredentialService credentialService;
    @Autowired
    CompanyService companyService;
    
    @PostMapping("/api/{companyId}/add-credential")
    public ResponseEntity<Void> createCredential(@PathVariable String companyId, @RequestBody Credential credential, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseAuth.getInstance().verifyIdToken(idToken);
            
            credential.setCompanyId(companyId);
            credentialService.createCredential(credential);
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/{companyId}/credentials")
    public ResponseEntity<List<Credential>> getCredentials(@PathVariable String companyId, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseAuth.getInstance().verifyIdToken(idToken);
            
            List<Credential> credentials = credentialService.getCredentialsByCompanyId(companyId);
            
            return ResponseEntity.status(HttpStatus.OK).body(credentials);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/api/credentials/{credentialId}")
    public ResponseEntity<Void> deleteCredential(@PathVariable String credentialId, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String userId = decodedToken.getUid();

            if (!companyService.getCompanyById(credentialService.getCredentialById(credentialId).getCompanyId()).getUserId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            credentialService.deleteCredential(credentialId);
            return ResponseEntity.status(HttpStatus.OK).build();

        }
        catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/update-credential")
    public ResponseEntity<Void> updateCredential(@RequestBody Credential credential, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseAuth.getInstance().verifyIdToken(idToken);
            
            credentialService.updateCredential(credential);
            
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
