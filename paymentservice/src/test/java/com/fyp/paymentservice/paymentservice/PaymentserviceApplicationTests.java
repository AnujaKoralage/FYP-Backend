package com.fyp.paymentservice.paymentservice;

import com.fyp.paymentservice.paymentservice.Component.UrlPropertyBundle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PaymentserviceApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        UrlPropertyBundle urlPropertyBundle = new UrlPropertyBundle();
        String url = "http://localhost:8081/api/v1/wallet" + "/trader/update";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiVVNFUl9DTElFTlRfUkVTT1VSQ0UiLCJVU0VSX0FETUlOX1JFU09VUkNFIl0sInVzZXJfbmFtZSI6ImFudWphIiwic2NvcGUiOlsicm9sZV90cmFkZXIiXSwiaWQiOjUsImV4cCI6MTU4Nzk5NjgzNiwiYXV0aG9yaXRpZXMiOlsiY2FuX3VwZGF0ZV9laG9sZSIsImNhbl9jYW5jZWxfZWhvbGUiLCJjYW5fcmVhZF9laG9sZSIsImNhbl9jcmVhdGVfZWhvbGUiLCJjYW5fY2FuY2VsX29yZGVyIiwiY2FuX3JlYWRfb3JkZXIiLCJjYW5fbWFrZV9wYXltZW50IiwiY2FuX3JlYWRfcGF5bWVudCIsImNhbl91cGRhdGVfb3JkZXIiLCJyb2xlX3RyYWRlciIsImNhbl9jcmVhdGVfb3JkZXIiXSwianRpIjoiOTMzNDE0N2UtMDU1OS00MWUxLTkxMjYtMTg3MjhjZThmNzYxIiwiZW1haWwiOiJhbnVqYUBnbWFpbC5jb20iLCJjbGllbnRfaWQiOiJVU0VSX0NMSUVOVF9BUFAifQ.ekMu9WRo5YsAo0YrzptNmeRqvHQaj5NQyN61eTQ3dikwILzhxCmXLx2gusGOADtcF6ngDercMJw2134Mknyc2pZrWwEcYuQIjee69xbYfJy3SUzSfP9NDjBywzcn50PwlFiDQ3LX71ZijnwWG0AHX71aMi2C14TP1S06Gx477IuPVWXR9qo4vGyCx1YaJfhNzsAJrNy8zGBnDY1C5YPXRpFL3y0lZ9SjjWbm-3u1oyP9n6Y47-IDImMnQkTdHNyfweD6kwV75jF9-08WY3yhQuPWqiaZyFExdOCiHIep9rFeiAmAxp3hUruY_hFwWXT03IZsQGSihnWx_d6tIwMghg" );
        headers.add("Content-Type", "application/json");

        Map<String, Object> walletObject = new HashMap<>();
        walletObject.put("paymentTransactionId", "Test43");
        walletObject.put("amount", 12.0);
        walletObject.put("mathEnum", "PLUS");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(walletObject, headers);
        System.out.println(url);
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT, request, ResponseEntity.class);
        System.out.println(response);
    }

}
