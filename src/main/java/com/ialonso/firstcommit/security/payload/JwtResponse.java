package com.ialonso.firstcommit.security.payload;

import com.ialonso.firstcommit.entities.Role;

import java.util.Set;

public class JwtResponse {

    private String token;
    private String username;
    private Set<Role> roles;

    public JwtResponse() {
    }

    public JwtResponse(String token, String username, Set<Role> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}

