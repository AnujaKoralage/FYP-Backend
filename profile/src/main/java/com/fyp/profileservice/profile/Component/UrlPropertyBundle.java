package com.fyp.profileservice.profile.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:URL.properties")
public class UrlPropertyBundle {

    @Value("${wallet.service.url}")
    private String walletServiceUrl;

    public String getWalletServiceUrl() {
        return walletServiceUrl;
    }

    public void setWalletServiceUrl(String walletServiceUrl) {
        this.walletServiceUrl = walletServiceUrl;
    }
}
