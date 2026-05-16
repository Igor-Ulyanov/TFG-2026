package com.igorul.authapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_org_role")
public class UserOrgRole {

    @EmbeddedId
    private UserOrgRoleId id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("organizationId")
    private Organization organization;

    @ManyToOne
    @MapsId("roleId")
    private Role role;

    public UserOrgRole() {}

    public UserOrgRole(User user, Organization organization, Role role) {
        this.user = user;
        this.organization = organization;
        this.role = role;
        this.id = new UserOrgRoleId(
                user.getId(),
                organization.getId(),
                role.getId()
        );
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}