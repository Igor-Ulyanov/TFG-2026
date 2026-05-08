package com.igorul.authapi.controller;

import com.igorul.authapi.model.Organization;
import com.igorul.authapi.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orgs")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {this.organizationService = organizationService;}

    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @DeleteMapping("/{id}")
    public void deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
    }

    @PutMapping("/{id}")
    public Organization updateUser(@PathVariable Long id,
                           @RequestBody Organization org) {

        return organizationService.updateOrganization(id, org);
    }
}