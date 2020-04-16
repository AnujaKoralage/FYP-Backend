package com.fyp.profileservice.profile.Util;

import java.io.Serializable;

public class CustomPrincipal implements Serializable {
    private String username;
    private String email;

    public CustomPrincipal(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public CustomPrincipal() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
