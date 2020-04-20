package com.fyp.walletservice.walletservice.Components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:URL.properties")
public class UrlPropertyBundle {

    @Value("${profile.service.url}")
    private String profileServiceURL;

    public String getProfileServiceURL() {
        return profileServiceURL;
    }

    public void setProfileServiceURL(String profileServiceURL) {
        this.profileServiceURL = profileServiceURL;
    }
}
