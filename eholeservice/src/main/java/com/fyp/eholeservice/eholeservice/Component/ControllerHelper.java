package com.fyp.eholeservice.eholeservice.Component;

import com.fyp.eholeservice.eholeservice.Util.CustomPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class ControllerHelper {

    OAuth2AuthenticationDetails details = null;

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
