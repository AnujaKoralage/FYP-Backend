package com.fyp.paymentservice.paymentservice.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class ResourceServerConfigurations extends ResourceServerConfigurerAdapter {

    @Autowired
    public TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll().and().cors().disable().csrf().disable().httpBasic().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .accessDeniedHandler(
//                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.addAllowedOrigin("*");
                config.setAllowCredentials(true);
                return config;
            }
        });
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()

                .antMatchers("/api/v1/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("USER_CLIENT_RESOURCE").tokenStore(tokenStore);
    }

}
