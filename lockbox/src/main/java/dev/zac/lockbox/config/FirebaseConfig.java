package dev.zac.lockbox.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class FirebaseConfig {
    
    @Bean
    public Firestore firestore() throws IOException {
        // Load environment variables
        Dotenv dotenv = Dotenv.load();
        
        // Get Firebase credentials from environment variable
        String firebaseCredentialsBase64 = dotenv.get("FIREBASE_CREDENTIALS");
        byte[] decodedCredentials = Base64.getDecoder().decode(firebaseCredentialsBase64);
        InputStream credentialsStream = new ByteArrayInputStream(decodedCredentials);

        // Initialize Firebase
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                .build();
            
            FirebaseApp.initializeApp(options);
        }
        
        // Return Firestore instance
        return FirestoreClient.getFirestore();
    }
}
