package com.fyp.paymentservice.paymentservice.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ControllerHelper {

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity updateWallet(String scope, HttpEntity<Map<String, String>> request) {
        String url = null;
        if (scope.equals("role_trader")) {
            url = urlPropertyBundle.getWaletUrl() + "/trader/update";
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity<String>("Success", HttpStatus.OK);
            } else {
                System.out.println("Unable to upload wallet");
                return new ResponseEntity(response.getStatusCode());
            }
        } else {
            url = urlPropertyBundle.getWaletUrl() + "/investor/update";
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity<String>("Success", HttpStatus.OK);
            } else {
                System.out.println("Unable to update wallet");
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
