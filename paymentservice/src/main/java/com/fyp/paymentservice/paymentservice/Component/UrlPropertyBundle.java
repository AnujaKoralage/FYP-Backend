package com.fyp.paymentservice.paymentservice.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:URL.properties")
public class UrlPropertyBundle {

    @Value("${profile.service.url}")
    private String profileServiceURL;

    @Value("${wallet.servce.url}")
    private
    String waletUrl;

    public String getProfileServiceURL() {
        return profileServiceURL;
    }

    public void setProfileServiceURL(String profileServiceURL) {
        this.profileServiceURL = profileServiceURL;
    }

    public String getWaletUrl() {
        return waletUrl;
    }

    public void setWaletUrl(String waletUrl) {
        this.waletUrl = waletUrl;
    }
}
