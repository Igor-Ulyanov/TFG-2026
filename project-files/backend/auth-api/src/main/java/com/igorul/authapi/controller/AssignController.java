package com.igorul.authapi.controller;

import com.igorul.authapi.model.UserOrgRole;
import com.igorul.authapi.dto.AssignRoleRequest;
import com.igorul.authapi.service.AssignService;
import com.igorul.authapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assign")
@Tag(name = "Assignments", description = "Assign roles to users within organizations")
public class AssignController {

    private final AssignService assignService;
    private final AuthService authService;


    public AssignController (AssignService assignService, AuthService authService) {
        this.assignService = assignService;
        this.authService = authService;
    }

    @Operation(summary = "Assign a user to a role in an org")
    @PostMapping
    @PreAuthorize("@authService.canAssignRole(authentication, #assignRoleRequest)")
    public UserOrgRole assignRole(@Valid @RequestBody AssignRoleRequest assignRoleRequest){
        return assignService.assignRole(assignRoleRequest);
    }

    @Operation(summary = "View list of roles for a user")
    @GetMapping("/user-roles/")
    @PreAuthorize("@authService.hasPermission(authentication, 'ASSIGN_ROLE')")
    public List<UserOrgRole> getUserRoles(
            @RequestParam String username) {

        return assignService.getUserRoles(username);
    }

    @Operation(summary = "Unassign a role to a user")
    @DeleteMapping
    public void removeRole(@RequestBody AssignRoleRequest request) {
        assignService.removeRole(request);
    }

}