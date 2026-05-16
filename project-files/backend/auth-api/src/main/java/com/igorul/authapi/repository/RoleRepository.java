package com.igorul.authapi.repository;

import com.igorul.authapi.model.Organization;
import com.igorul.authapi.model.Role;
import com.igorul.authapi.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    boolean existsByNameAndOrganizationAndIdNot(String name, Organization organization, Long id);

    boolean existsByNameAndOrganization(String name, Organization org);

    Role findByNameAndOrganization(@NotBlank(message = "Please specify a role to assign") String roleName, Organization org);
}