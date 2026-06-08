package com.igorul.authapi.controller;

import com.igorul.authapi.dto.CheckRequest;
import com.igorul.authapi.dto.LoginRequest;
import com.igorul.authapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization and authentication", description = "Login in and check for existing permissions")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Log in with a username and password, contact the admin for a change of password")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(Map.of("token", token));
    }

    @Operation(summary = "Check to see if a user can preform a certain action in an org")
    @PostMapping("/check")
    public boolean checkPermission(@RequestBody CheckRequest request) {

            return authService.checkPermission(
                    request.getUsername(),
                    request.getOrganizationName(),
                    request.getPermission()
            );
        }
    }