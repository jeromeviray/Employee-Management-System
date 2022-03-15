package com.project.websecurity.jwtUtil.response;

import com.project.account.model.AccountRole;

public class JwtResponse {
//    private final String type = "Bearer ";
    private String username;
    private String jwtToken;
    private AccountRole role;
    private String type;

    public JwtResponse() {
    }

    public JwtResponse( String username, String jwtToken ) {
        this.username = username;
        this.jwtToken = jwtToken;
    }

    public JwtResponse( String username, String jwtToken, AccountRole role ) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.role = role;
    }

    public JwtResponse( String username, String jwtToken, AccountRole role, String type ) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.role = role;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken( String jwtToken ) {
        this.jwtToken = jwtToken;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole( AccountRole role ) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "username='" + username + '\'' +
                ", jwtToken='" + jwtToken + '\'' +
                ", role=" + role +
                ", type='" + type + '\'' +
                '}';
    }
}
