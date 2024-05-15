package com.example.springboot.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.springboot.properties.SecurityProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    private final SecurityProperties securityProperties;

    @Autowired
    public FirebaseConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Primary
    @Bean
    public FirebaseApp firebaseInit() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl(securityProperties.getFirebaseProps().getDatabaseUrl())
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            System.out.println("Firebase Initialize");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FirebaseApp.getInstance();
    }
}
