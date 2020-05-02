package com.fyp.eholeservice.eholeservice.Util;

import java.io.Serializable;

public class CustomPrincipal implements Serializable {

    private String username;
    private String email;
    private String id;

    public CustomPrincipal() {
    }

    public CustomPrincipal(String username, String email) {
        this.setUsername(username);
        this.setEmail(email);
    }

    public CustomPrincipal(String username, String email, String id) {
        this.username = username;
        this.email = email;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
