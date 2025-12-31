package com.smartvillage.smart_village_backend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseTokenService {

    public FirebaseToken verifyToken(String token) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Firebase token", e);
        }
    }
}
