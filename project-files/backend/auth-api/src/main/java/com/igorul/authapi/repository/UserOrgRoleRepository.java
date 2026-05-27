package com.igorul.authapi.repository;

import com.igorul.authapi.model.User;
import com.igorul.authapi.model.UserOrgRole;
import com.igorul.authapi.model.UserOrgRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrgRoleRepository
        extends JpaRepository<UserOrgRole, UserOrgRoleId> {
    List<UserOrgRole> findByUser(User user);

    List<UserOrgRole> findByUserIdAndOrganizationId(Long id, Long orgId);
}