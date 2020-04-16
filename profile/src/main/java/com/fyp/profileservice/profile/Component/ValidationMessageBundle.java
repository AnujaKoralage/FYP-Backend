package com.fyp.profileservice.profile.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:ValidationMessages.properties")
public class ValidationMessageBundle {

    @Value("${user.email.blank}")
    private String blankEmail;

    @Value("${user.password.blank}")
    private String blankPassword;

    @Value(("${user.username.blank}"))
    private String blankUsername;

    @Value("${user.alreadyExist}")
    private String userAlreadyExist;

    @Value("${user.email.invalid}")
    private String UserEmailInvalid;

    @Value("${user.create.faild}")
    private String userCreationFaild;

    public String getBlankEmail() {
        return blankEmail;
    }

    public void setBlankEmail(String blankEmail) {
        this.blankEmail = blankEmail;
    }

    public String getBlankPassword() {
        return blankPassword;
    }

    public void setBlankPassword(String blankPassword) {
        this.blankPassword = blankPassword;
    }

    public String getBlankUsername() {
        return blankUsername;
    }

    public void setBlankUsername(String blankUsername) {
        this.blankUsername = blankUsername;
    }

    public String getUserAlreadyExist() {
        return userAlreadyExist;
    }

    public void setUserAlreadyExist(String userAlreadyExist) {
        this.userAlreadyExist = userAlreadyExist;
    }

    public String getUserEmailInvalid() {
        return UserEmailInvalid;
    }

    public void setUserEmailInvalid(String userEmailInvalid) {
        UserEmailInvalid = userEmailInvalid;
    }

    public String getUserCreationFaild() {
        return userCreationFaild;
    }

    public void setUserCreationFaild(String userCreationFaild) {
        this.userCreationFaild = userCreationFaild;
    }
}
