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

    @PostMapping("/api/register")
    public ResponseEntity<User> createUser(@RequestHeader("Authorization") String authHeader, @RequestBody User user) {
        String idToken = authHeader.replace("Bearer ", "");
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            if (!decodedToken.getUid().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            User existingUser = userService.getUserById(user.getId());
            if (existingUser != null) {
                return ResponseEntity.ok(existingUser);
            }

            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUserById(user.getId()));

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<Void> loginUser(@RequestHeader("Authorization") String authHeader) {
        String idToken = authHeader.replace("Bearer ", "");
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            User user = userService.getUserById(uid);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}