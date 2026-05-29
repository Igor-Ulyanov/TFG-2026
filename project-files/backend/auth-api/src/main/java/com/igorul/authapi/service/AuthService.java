package com.igorul.authapi.service;

import com.igorul.authapi.dto.AssignRoleRequest;
import com.igorul.authapi.dto.CreateRoleRequest;
import com.igorul.authapi.model.*;
import com.igorul.authapi.repository.*;
import com.igorul.authapi.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserOrgRoleRepository userOrgRoleRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPass_hash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user);
    }

    public boolean hasPermissionInOrg(Authentication authentication, Long orgId, String permission) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        List<UserOrgRole> relations =
                userOrgRoleRepository.findByUserIdAndOrganizationId(
                        user.getId(),
                        orgId
                );

        for (UserOrgRole relation : relations) {

            Role role = relation.getRole();

            for (Permission p : role.getPermissions()) {

                if (p.getName().equals(permission)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasPermission(Authentication authentication, String permission) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        List<UserOrgRole> relations =
                userOrgRoleRepository.findByUserId(user.getId());

        for (UserOrgRole relation : relations) {

            Role role = relation.getRole();

            for (Permission p : role.getPermissions()) {

                if (p.getName().equals(permission)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canCreateRole(Authentication authentication, CreateRoleRequest request) {

        Organization org = organizationRepository
                .findByName(request.getOrganizationName());

        if (org == null) {
            return false;
        }

        return hasPermissionInOrg(
                authentication,
                org.getId(),
                "CREATE_ROLE"
        );

    }

    public boolean canDeleteRole(Authentication authentication, Long id) {

        Role role = roleRepository.findById(id).orElse(null);

        return hasPermissionInOrg(
                authentication,
                role.getOrganization().getId(),
                "DELETE_ROLE"
        );
    }

    public boolean canUpdateRole(Authentication authentication, Long id, CreateRoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElse(null);

        if (role == null) {
            return false;
        }

        Long currentOrgId = role.getOrganization().getId();
        Long newOrgId = organizationRepository.findByName(request.getOrganizationName()).getId();

        boolean hasCurrent =
                hasPermissionInOrg(
                        authentication,
                        currentOrgId,
                        "UPDATE_ROLE"
                );

        if (!hasCurrent) {
            return false;
        }

        // si cambia de organización
        if (!currentOrgId.equals(newOrgId)) {

            return hasPermissionInOrg(
                    authentication,
                    newOrgId,
                    "UPDATE_ROLE"
            );
        }

        return true;
    }

    public boolean canAssignRole(Authentication authentication, AssignRoleRequest request){

        Organization org = organizationRepository
                .findByName(request.getOrganizationName());

        if (org == null) {
            return false;
        }

        return hasPermissionInOrg(
                authentication,
                org.getId(),
                "ASSIGN_ROLE"
        );
    }
}