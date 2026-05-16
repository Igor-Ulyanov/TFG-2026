package com.igorul.authapi.service;

import com.igorul.authapi.model.*;
import com.igorul.authapi.dto.AssignRoleRequest;
import com.igorul.authapi.repository.UserOrgRoleRepository;
import com.igorul.authapi.repository.UserRepository;
import com.igorul.authapi.repository.RoleRepository;
import com.igorul.authapi.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignService {

    private final UserOrgRoleRepository userOrgRoleRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;


    public AssignService(UserOrgRoleRepository userOrgRoleRepository, UserRepository userRepository, OrganizationRepository organizationRepository, RoleRepository roleRepository){
        this.userOrgRoleRepository = userOrgRoleRepository;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.roleRepository = roleRepository;
    }

    public UserOrgRole assignRole(AssignRoleRequest request) {

        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) throw new RuntimeException("User not found");

        Organization org = organizationRepository
                .findByName(request.getOrganizationName());
        if (org == null) throw new RuntimeException("Org not found");

        Role role = roleRepository
                .findByNameAndOrganization(request.getRoleName(), org);
        if (role == null) throw new RuntimeException("Role not found in org");

        UserOrgRole uor = new UserOrgRole(user, org, role);

        return userOrgRoleRepository.save(uor);
    }

    public List<UserOrgRole> getUserRoles(String username) {

        User user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("User not found");

        List<UserOrgRole> assignments =
                userOrgRoleRepository.findByUser(user);

        return assignments;
    }

    public void removeRole(AssignRoleRequest request) {

        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) throw new RuntimeException("User not found");

        Organization org = organizationRepository.findByName(request.getOrganizationName());
        if (org == null) throw new RuntimeException("Organization not found");

        Role role = roleRepository
                .findByNameAndOrganization(request.getRoleName(), org);
        if (role == null) throw new RuntimeException("Role not found in org");

        UserOrgRoleId id = new UserOrgRoleId(
                user.getId(),
                org.getId(),
                role.getId()
        );

        if (!userOrgRoleRepository.existsById(id)) {
            throw new RuntimeException("Role assignment does not exist");
        }

        userOrgRoleRepository.deleteById(id);
    }

}