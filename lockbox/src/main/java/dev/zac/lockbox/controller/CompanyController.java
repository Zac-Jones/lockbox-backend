package dev.zac.lockbox.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import dev.zac.lockbox.entity.Company;
import dev.zac.lockbox.entity.Credential;
import dev.zac.lockbox.entity.TwoFactorAuth;
import dev.zac.lockbox.service.CompanyService;
import dev.zac.lockbox.service.CredentialService;
import dev.zac.lockbox.service.TwoFactorAuthService;

@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private TwoFactorAuthService twoFactorAuthService;
    
    @PostMapping("/api/add-company")
    public ResponseEntity<Void> createCompany(@RequestBody Company company, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String userId = decodedToken.getUid();
            
            company.setUserId(userId);
            
            companyService.createCompany(company);
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/companies")
    public ResponseEntity<List<Company>> getCompanies(@RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String userId = decodedToken.getUid();

            List<Company> companies = companyService.getCompaniesByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(companies);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/companies/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String companyId, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String userId = decodedToken.getUid();

            if (!companyService.getCompanyById(companyId).getUserId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            companyService.deleteCompany(companyId);
            for (Credential credential : credentialService.getCredentialsByCompanyId(companyId))
                credentialService.deleteCredential(credential.getId());

            for (TwoFactorAuth twoFactorAuth : twoFactorAuthService.getTwoFactorAuthsByCompanyId(companyId))
                twoFactorAuthService.deleteTwoFactorAuth(twoFactorAuth.getId());

            return ResponseEntity.status(HttpStatus.OK).build();

        }
        catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}