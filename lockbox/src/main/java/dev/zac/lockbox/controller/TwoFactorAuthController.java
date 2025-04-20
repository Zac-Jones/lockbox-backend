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

import dev.zac.lockbox.entity.TwoFactorAuth;
import dev.zac.lockbox.service.CompanyService;
import dev.zac.lockbox.service.TwoFactorAuthService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TwoFactorAuthController {
    @Autowired
    TwoFactorAuthService twoFactorAuthService;
    @Autowired
    CompanyService companyService;

    @PostMapping("/api/{companyId}/add-2fa")
    public ResponseEntity<Void> createTwoFactorAuth(@PathVariable String companyId, @RequestBody TwoFactorAuth twoFactorAuth, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseAuth.getInstance().verifyIdToken(idToken);
            
            twoFactorAuth.setCompanyId(companyId);
            twoFactorAuthService.createTwoFactorAuth(twoFactorAuth);
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/{companyId}/2fas")
    public ResponseEntity<List<TwoFactorAuth>> getTwoFactorAuths(@PathVariable String companyId, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseAuth.getInstance().verifyIdToken(idToken);
            
            List<TwoFactorAuth> twoFactorAuths = twoFactorAuthService.getTwoFactorAuthsByCompanyId(companyId);
            
            return ResponseEntity.status(HttpStatus.OK).body(twoFactorAuths);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/api/2fas/{twoFactorAuthId}")
    public ResponseEntity<Void> deleteTwoFactorAuth(@PathVariable String twoFactorAuthId, @RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String userId = decodedToken.getUid();

            if (!companyService.getCompanyById(twoFactorAuthService.getTwoFactorAuthById(twoFactorAuthId).getCompanyId()).getUserId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            twoFactorAuthService.deleteTwoFactorAuth(twoFactorAuthId);
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
