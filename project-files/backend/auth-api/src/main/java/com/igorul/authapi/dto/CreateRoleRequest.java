package com.igorul.authapi.dto;

import java.util.List;

public class CreateRoleRequest {

    private String name;
    private String description;
    private String organizationName;
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