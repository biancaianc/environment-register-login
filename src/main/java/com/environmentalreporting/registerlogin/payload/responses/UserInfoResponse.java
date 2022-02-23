package com.environmentalreporting.registerlogin.payload.responses;

import java.util.List;


public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    private Long points;

    public UserInfoResponse(Long id, String username, String email, List<String> roles, Long points) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.points=points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}

