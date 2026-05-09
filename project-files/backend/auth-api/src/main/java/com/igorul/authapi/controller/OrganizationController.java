package com.igorul.authapi.controller;

import com.igorul.authapi.model.Organization;
import com.igorul.authapi.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orgs")
@Tag(name = "Organizations", description = "Org management endpoints for CRUD operations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {this.organizationService = organizationService;}

    @Operation(summary = "Create a new org")
    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @Operation(summary = "View list of all orgs")
    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @Operation(summary = "Delete an org by their id")
    @DeleteMapping("/{id}")
    public void deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
    }

    @Operation(summary = "Edit an org's data by their id (partial editing accepted)")
    @PutMapping("/{id}")
    public Organization updateUser(@PathVariable Long id,
                           @RequestBody Organization org) {

        return organizationService.updateOrganization(id, org);
    }
}