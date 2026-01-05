package com.smartvillage.smart_village_backend.controller;

import com.smartvillage.smart_village_backend.dto.RegisterRequest;
import com.smartvillage.smart_village_backend.entity.User;
import com.smartvillage.smart_village_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        // prevent duplicate registration
        if (userRepository.existsByFirebaseUid(request.getUid())) {
            return ResponseEntity.badRequest().body("User already registered");
        }

        User user = new User();
        user.setFirebaseUid(request.getUid());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setRole("CITIZEN");

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
