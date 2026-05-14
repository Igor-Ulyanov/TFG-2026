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

        if (organizationRepository.existsByName(organization.getName())){
            throw new RuntimeException("There is already an organization with this name");
        };

        if (!organizationRepository.existsById(organization.getParent_id()) && organization.getParent_id() != null){
            throw new RuntimeException("There is no org with this id");
        };

        return organizationRepository.save(organization);
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public void deleteOrganization(Long id) {

        if (!organizationRepository.existsById(id)) {
            throw new RuntimeException("Organization not found");
        }

        organizationRepository.deleteById(id);
    }

    public Organization updateOrganization(Long id, Organization updatedOrg) {

        Organization org = organizationRepository.findById(id)
                .orElseThrow();

        if (updatedOrg.getName() != null) {

            if (organizationRepository.existsByName(updatedOrg.getName())){
                throw new RuntimeException("There is already an organization with this name");
            };

            org.setName(updatedOrg.getName());
        }

        if (updatedOrg.getDescription() != null) {
            org.setDescription(updatedOrg.getDescription());
        }

        if (updatedOrg.getParent_id() != null) {

            if (!organizationRepository.existsById(updatedOrg.getParent_id())){
                throw new RuntimeException("There is no org with this id");
            };

            org.setParent_id(updatedOrg.getParent_id());
        }

        return organizationRepository.save(org);
    }
}