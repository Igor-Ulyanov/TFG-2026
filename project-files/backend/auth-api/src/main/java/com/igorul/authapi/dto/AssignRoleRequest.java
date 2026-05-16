package com.igorul.authapi.dto;
import jakarta.validation.constraints.NotBlank;

public class AssignRoleRequest {

    private String username;
    private String organizationName;
    private String roleName;

    public AssignRoleRequest() {}

    @NotBlank(message = "Please specify a user")
    public String getUsername() { return username; }

    @NotBlank(message = "Please specify an org")
    public String getOrganizationName() { return organizationName; }

    @NotBlank(message = "Please specify a role to assign")
    public String getRoleName() { return roleName; }

    public void setUsername(String username) { this.username = username; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}