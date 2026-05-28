package com.igorul.authapi.controller;

import com.igorul.authapi.model.Organization;
import com.igorul.authapi.service.AuthService;
import com.igorul.authapi.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orgs")
@Tag(name = "Organizations", description = "Org management endpoints for CRUD operations")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final AuthService authService;


    public OrganizationController(OrganizationService organizationService, AuthService authService) {
        this.organizationService = organizationService;
        this.authService = authService;
    }

    @Operation(summary = "Create a new org")
    @PostMapping
    public Organization createOrganization(@Valid @RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @Operation(summary = "View list of all orgs")
    @GetMapping
    @PreAuthorize("@authService.hasPermission(authentication, 'READ_ORG')")
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @Operation(summary = "Delete an org by their id")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authService.hasPermissionInOrg(authentication, #id, 'DELETE_ORG')")
    public void deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
    }

    @Operation(summary = "Edit an org's data by their id (partial editing accepted)")
    @PutMapping("/{id}")
    @PreAuthorize("@authService.hasPermissionInOrg(authentication, #id, 'UPDATE_ORG')")
    public Organization updateOrganization(@PathVariable Long id,
                           @RequestBody Organization org) {

        return organizationService.updateOrganization(id, org);
    }
}