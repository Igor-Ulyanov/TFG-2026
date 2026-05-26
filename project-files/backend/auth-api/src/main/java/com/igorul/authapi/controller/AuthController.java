package com.igorul.authapi.controller;

import com.igorul.authapi.dto.LoginRequest;
import com.igorul.authapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(Map.of("token", token));
    }
}