package com.fyp.tradeservice.traderservice.Component;

import com.fyp.tradeservice.traderservice.Util.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ControllerHelper {

    OAuth2AuthenticationDetails details = null;

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @Autowired
    RestTemplate restTemplate;

    public String getAccessToken(OAuth2Authentication authentication) {
        details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String accessToken = details.getTokenValue();
        return accessToken;
    }

    public String getScope(OAuth2Authentication authentication) {
        details = (OAuth2AuthenticationDetails) authentication.getDetails();
        Object[] array = authentication.getOAuth2Request().getScope().toArray();
        String scope = (String) array[0];
        return scope;
    }

    public CustomPrincipal getCustomPrincipal(OAuth2Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return principal;
    }

}
