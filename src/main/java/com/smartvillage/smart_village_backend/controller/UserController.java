package com.smartvillage.smart_village_backend.controller;

import com.smartvillage.smart_village_backend.dto.RegisterRequest;
import com.smartvillage.smart_village_backend.service.FirebaseTokenService;
import com.smartvillage.smart_village_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final FirebaseTokenService firebaseTokenService;

    public UserController(UserService userService,
                          FirebaseTokenService firebaseTokenService) {
        this.userService = userService;
        this.firebaseTokenService = firebaseTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody RegisterRequest request) {

        userService.registerUser(authHeader, request);
        return ResponseEntity.ok("User registered");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        var firebaseToken = firebaseTokenService.verifyToken(token);

        return ResponseEntity.ok(
                userService.getCurrentUser(firebaseToken.getUid())
        );
    }

}

