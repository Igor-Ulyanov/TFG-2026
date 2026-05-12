package com.igorul.authapi.service;

import com.igorul.authapi.model.Role;
import com.igorul.authapi.model.Permission;
import com.igorul.authapi.model.Organization;
import com.igorul.authapi.repository.RoleRepository;
import com.igorul.authapi.repository.PermissionRepository;
import com.igorul.authapi.repository.OrganizationRepository;
import com.igorul.authapi.dto.CreateRoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final OrganizationRepository organizationRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository, OrganizationRepository organizationRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.organizationRepository = organizationRepository;
    }


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role createRole(CreateRoleRequest request) {

        Organization org =
                organizationRepository.findByName(
                        request.getOrganizationName()
                );

        List<Permission> permissions =
                permissionRepository.findByNameIn(
                        request.getPermissions()
                );

        Role role = new Role();

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        role.setOrganization(org);

        role.setPermissions(permissions);

        return roleRepository.save(role);
    }

    public Role updateRole(Long id, CreateRoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow();

        if (request.getName() != null) {
            role.setName(request.getName());
        }

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        if (request.getOrganizationName() != null) {
            Organization newOrg = organizationRepository.findByName(
                    request.getOrganizationName()
            );

            role.setOrganization(newOrg);
        }

        if (request.getPermissions() != null) {
            List<Permission> newPermissions =
                    permissionRepository.findByNameIn(
                            request.getPermissions()
                    );
            role.setPermissions(newPermissions);
        }

        return roleRepository.save(role);
    }
}
