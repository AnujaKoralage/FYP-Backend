package com.fyp.eholeservice.eholeservice.Component;

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

    @Value("${payment.service.url}")
    private String eholePaymentUrl;

    @Value("${trading.service.url}")
    private String tradingUrl;

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

    public String getEholePaymentUrl() {
        return eholePaymentUrl;
    }

    public void setEholePaymentUrl(String eholePaymentUrl) {
        this.eholePaymentUrl = eholePaymentUrl;
    }

    public String getTradingUrl() {
        return tradingUrl;
    }

    public void setTradingUrl(String tradingUrl) {
        this.tradingUrl = tradingUrl;
    }
}
