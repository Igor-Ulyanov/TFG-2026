package com.igorul.authapi.controller;

import com.igorul.authapi.dto.CreateUserResponse;
import com.igorul.authapi.model.User;
import com.igorul.authapi.service.AuthService;
import com.igorul.authapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management endpoints for CRUD operations")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    @PreAuthorize("@authService.hasPermission(authentication, 'CREATE_USER')")
    public CreateUserResponse createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "View list of all users")
    @GetMapping
    @PreAuthorize("@authService.hasPermission(authentication, 'READ_USER')")
    public List<CreateUserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Delete a user by their id")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authService.hasPermission(authentication, 'DELETE_USER')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Edit a user's data by their id (partial editing accepted)")
    @PutMapping("/{id}")
    @PreAuthorize("@authService.hasPermission(authentication, 'UPDATE_USER')")
    public CreateUserResponse updateUser(@PathVariable Long id,
                           @RequestBody User user) {

        return userService.updateUser(id, user);
    }
}