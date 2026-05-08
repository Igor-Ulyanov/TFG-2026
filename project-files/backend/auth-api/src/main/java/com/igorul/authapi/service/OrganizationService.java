package com.igorul.authapi.service;

import com.igorul.authapi.model.Organization;
import com.igorul.authapi.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }

    public Organization updateOrganization(Long id, Organization updatedOrg) {

        Organization org = organizationRepository.findById(id)
                .orElseThrow();

        if (updatedOrg.getName() != null) {
            org.setName(updatedOrg.getName());
        }

        if (updatedOrg.getDescription() != null) {
            org.setDescription(updatedOrg.getDescription());
        }

        if (updatedOrg.getParentId() != null) {
            org.setParentId(updatedOrg.getParentId());
        }

        return organizationRepository.save(org);
    }
}