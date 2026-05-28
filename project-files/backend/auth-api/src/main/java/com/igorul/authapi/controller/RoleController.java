package com.igorul.authapi.controller;

import com.igorul.authapi.model.Role;
import com.igorul.authapi.dto.CreateRoleRequest;
import com.igorul.authapi.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.igorul.authapi.service.AuthService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Role management endpoints for CRUD operations")
public class RoleController{

    private final RoleService roleService;
    private final AuthService authService;

    public RoleController(RoleService roleService, AuthService authService){
        this.roleService = roleService;
        this.authService = authService;
    }

    @Operation(summary = "View list of all Roles from a specific org")
    @GetMapping("/{orgId}")
    @PreAuthorize("@authService.hasPermissionInOrg(authentication, #orgId, 'READ_ROLE')")
    public List<Role> getAllRoles(@PathVariable Long orgId) {
        return roleService.getAllRoles(orgId);
    }

    @Operation(summary = "Delete a role by it's id")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authService.canDeleteRole(authentication, #id)")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    @Operation(summary = "Create a new role")
    @PostMapping
    @PreAuthorize("@authService.canCreateRole(authentication, #request)")
    public Role createRole(@Valid @RequestBody CreateRoleRequest request) {
        return roleService.createRole(request);
    }

    @Operation(summary = "Edit a role's data by their id (partial editing accepted)")
    @PutMapping("/{id}")
    @PreAuthorize("@authService.canUpdateRole(authentication, #id, #request)")
    public Role updateRole(@PathVariable Long id,
                           @RequestBody CreateRoleRequest request) {

        return roleService.updateRole(id, request);
    }


}
