package com.igorul.authapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateRoleRequest {

    @NotBlank(message = "Role name is required")
    private String name;

    private String description;

    @NotBlank(message = "A role must belong to an org, please input")
    private String organizationName;

    @NotEmpty(message = "Permission list is required")
    private List<String> permissions;

    public CreateRoleRequest() {}

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public String getOrganizationName(){
        return this.organizationName;
    }

    public List<String> getPermissions(){
        return this.permissions;
    }

}