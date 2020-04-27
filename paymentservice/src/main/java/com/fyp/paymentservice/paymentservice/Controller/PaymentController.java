package com.fyp.paymentservice.paymentservice.Controller;

import com.fyp.paymentservice.paymentservice.Component.UrlPropertyBundle;
import com.fyp.paymentservice.paymentservice.DTO.PayDTO;
import com.fyp.paymentservice.paymentservice.DTO.TopupResponceDTO;
import com.fyp.paymentservice.paymentservice.Enum.PaymentStatusTypes;
import com.fyp.paymentservice.paymentservice.Enum.TransactionTypes;
import com.fyp.paymentservice.paymentservice.Service.PaypalService;
import com.fyp.paymentservice.paymentservice.Service.TopupService;
import com.fyp.paymentservice.paymentservice.Utils.CustomAccessTokenConverter;
import com.fyp.paymentservice.paymentservice.Utils.CustomPrincipal;
import com.fyp.paymentservice.paymentservice.Utils.URLUtils;
import com.google.gson.JsonObject;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/payment")
public class PaymentController {

    @Autowired
    PaypalService paypalService;

    @Autowired
    TopupService topupService;

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CustomAccessTokenConverter customAccessTokenConverter;

    public final String SUCCESS_URL = "pay/success";
    public final String CANCEL_URL = "pay/cancel";


    @PostMapping(path = "/topup")
    public ResponseEntity topup(@RequestBody PayDTO payDTO, HttpServletRequest request, OAuth2Authentication authentication) {

        Object[] array = authentication.getOAuth2Request().getScope().toArray();
        String scope = (String) array[0];

        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        String cancelUrl = URLUtils.getBaseURl(request) + "/" + CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + SUCCESS_URL;
        try {
            Payment payment = paypalService.createPayment(payDTO.getPrice(), payDTO.getCurrency(), payDTO.getMethod(), payDTO.getIntent(), payDTO.getDescription(), cancelUrl, successUrl);

            for (Links link: payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    String url = urlPropertyBundle.getProfileServiceURL() + "/trader/id?username=" + customPrincipal.getUsername();
                    if (scope.equals("role_trader")){
                        ResponseEntity<Long> exchangeTr = restTemplate.exchange(url, HttpMethod.GET, null, Long.class);
                        if (exchangeTr.getStatusCode().is2xxSuccessful()) {
                            String transactionId = topupService.saveTransaction(payDTO, customPrincipal.getId(), 1);
                            TopupResponceDTO topupResponceDTO = new TopupResponceDTO(link.getHref(), transactionId);
                            return new ResponseEntity<TopupResponceDTO>(topupResponceDTO, HttpStatus.OK);
                        } else {
                            System.out.println("Failed to exchange trader id");
                            return new ResponseEntity(exchangeTr.getStatusCode());
                        }
                    } else {
                        url = urlPropertyBundle.getProfileServiceURL() + "/investor/id?username=" + customPrincipal.getUsername();
                        ResponseEntity<Long> exchangeIn = restTemplate.exchange(url, HttpMethod.GET, null, Long.class);
                        if (exchangeIn.getStatusCode().is2xxSuccessful()) {
                            String transactionId = topupService.saveTransaction(payDTO, customPrincipal.getId(), 2);
                            TopupResponceDTO topupResponceDTO = new TopupResponceDTO(link.getHref(), transactionId);
                            return new ResponseEntity<TopupResponceDTO>(topupResponceDTO, HttpStatus.OK);
                        } else {
                            System.out.println("Failed to exchange trader id");
                            return new ResponseEntity(exchangeIn.getStatusCode());
                        }
                    }

                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = CANCEL_URL)
    public String cancelPay() {
        return "failure";
    }

    @GetMapping(path = SUCCESS_URL)
    public ResponseEntity successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("transactionId") String transactionId, OAuth2Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String accessToken = details.getTokenValue();

        Object[] array = authentication.getOAuth2Request().getScope().toArray();
        String scope = (String) array[0];

        if (transactionId == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            String url = urlPropertyBundle.getWaletUrl() + "/trader/update";
            Payment payment = paypalService.executePayment(paymentId, payerId);
            List<Transaction> transactions = payment.getTransactions();
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                Long userId = topupService.updateTransaction(PaymentStatusTypes.SUCCESS, payment.getId(), transactionId, customPrincipal.getId());

                if (userId != null) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Authorization", "Bearer " + accessToken);
                    headers.add("Content-Type", "application/json");

                    Map<String, String> walletObject = new HashMap<>();
                    walletObject.put("paymentTransactionId", transactionId);
                    walletObject.put("amount", transactions.get(0).getRelatedResources().get(0).getSale().getAmount().getTotal());
                    walletObject.put("mathEnum", "PLUS");
                    HttpEntity<Map<String, String>> request = new HttpEntity<>(walletObject, headers);

                    if (scope.equals("role_trader")) {
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
                } else {
                    System.out.println("failed to update transaction");
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity( HttpStatus.BAD_REQUEST);
    }


}
