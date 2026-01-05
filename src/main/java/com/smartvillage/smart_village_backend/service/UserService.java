package com.smartvillage.smart_village_backend.service;

import com.google.firebase.auth.FirebaseToken;
import com.smartvillage.smart_village_backend.dto.RegisterRequest;
import com.smartvillage.smart_village_backend.entity.Role;
import com.smartvillage.smart_village_backend.entity.User;
import com.smartvillage.smart_village_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FirebaseTokenService firebaseTokenService;

    public UserService(UserRepository userRepository,
                       FirebaseTokenService firebaseTokenService) {
        this.userRepository = userRepository;
        this.firebaseTokenService = firebaseTokenService;
    }

    public User getCurrentUser(String firebaseUid) {
        return userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("User not registered"));
    }

    public void assertAdmin(String firebaseUid) {
        User user = getCurrentUser(firebaseUid);
        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Admin access required");
        }
    }

    public void registerUser(String authHeader, RegisterRequest request) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        FirebaseToken firebaseToken = firebaseTokenService.verifyToken(token);
        String uidFromToken = firebaseToken.getUid();

        if (!uidFromToken.equals(request.getUid())) {
            throw new RuntimeException("UID mismatch");
        }

        if (userRepository.findByFirebaseUid(uidFromToken).isPresent()) {
            throw new RuntimeException("User already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setEmail(request.getEmail());
        user.setFirebaseUid(uidFromToken);
        user.setRole(Role.CITIZEN);

        userRepository.save(user);
    }
}
