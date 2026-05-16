package com.igorul.authapi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserOrgRoleId implements Serializable {

    private Long userId;
    private Long organizationId;
    private Long roleId;

    public UserOrgRoleId() {}

    public UserOrgRoleId(Long userId, Long organizationId, Long roleId) {
        this.userId = userId;
        this.organizationId = organizationId;
        this.roleId = roleId;
    }

    // equals + hashCode REQUIRED
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserOrgRoleId)) return false;
        UserOrgRoleId that = (UserOrgRoleId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(organizationId, that.organizationId) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, organizationId, roleId);
    }
}