package dev.zac.lockbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import dev.zac.lockbox.entity.User;
import dev.zac.lockbox.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestHeader("Authorization") String authHeader, @RequestBody User user) {
        String idToken = authHeader.replace("Bearer ", "");
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            if (!decodedToken.getUid().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("UID mismatch");
            }

            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        }
    }
}