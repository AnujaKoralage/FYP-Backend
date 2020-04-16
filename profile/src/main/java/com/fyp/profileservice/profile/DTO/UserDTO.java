package com.fyp.profileservice.profile.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fyp.profileservice.profile.Enum.UserRoleEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDTO {

    @JsonIgnore
    private
    Long id;
    @NotBlank(message = "{user.email.blank}")
    @Email(message = "user.email.invalid")
    private String email;
    @NotBlank(message = "{user.username.blank}")
    private String username;
    @NotBlank(message = "{user.password.blank}")
    private String password;
    @JsonIgnore
    private UserRoleEnum userRoleEnum;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleEnum getUserRoleEnum() {
        return userRoleEnum;
    }

    public void setUserRoleEnum(UserRoleEnum userRoleEnum) {
        this.userRoleEnum = userRoleEnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRoleEnum=" + userRoleEnum +
                '}';
    }
}
