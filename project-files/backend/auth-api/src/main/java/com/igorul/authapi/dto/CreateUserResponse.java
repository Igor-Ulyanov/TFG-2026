package com.igorul.authapi.dto;

public class CreateUserResponse {

    public Long userId;
    public String username;
    public String email;
    public String passwordStatus;

    public CreateUserResponse(){
    }

    public CreateUserResponse(Long id, String username, String email, String passwordStatus){
        this.userId = id;
        this.email = email;
        this.username = username;
        this.passwordStatus = passwordStatus;

    }

    public String getEmail() {
        return email;
    }

    public String getPasswordStatus() {
        return passwordStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordStatus(String passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
