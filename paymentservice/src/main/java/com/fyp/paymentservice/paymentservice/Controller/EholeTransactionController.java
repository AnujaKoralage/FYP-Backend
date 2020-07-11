package com.fyp.paymentservice.paymentservice.Controller;

import com.fyp.paymentservice.paymentservice.Component.ControllerHelper;
import com.fyp.paymentservice.paymentservice.Component.UrlPropertyBundle;
import com.fyp.paymentservice.paymentservice.DTO.InvestTransactionDTO;
import com.fyp.paymentservice.paymentservice.DTO.PaybackDTO;
import com.fyp.paymentservice.paymentservice.DTO.PaybackTransactionDTO;
import com.fyp.paymentservice.paymentservice.DTO.WalletDTO;
import com.fyp.paymentservice.paymentservice.Enum.UserType;
import com.fyp.paymentservice.paymentservice.Service.EholeTransactionService;
import com.fyp.paymentservice.paymentservice.Utils.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/invest")
public class EholeTransactionController {

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ControllerHelper controllerHelper;

    @Autowired
    EholeTransactionService eholeTransactionService;

    @PostMapping(path = "/save")
    public ResponseEntity saveTransaction(@RequestBody InvestTransactionDTO dto, OAuth2Authentication authentication) {

        if (dto.getAmount() == 0 || dto.getEholeId() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String accessToken = details.getTokenValue();
        String url = urlPropertyBundle.getWaletUrl();

        Object[] array = authentication.getOAuth2Request().getScope().toArray();
        String scope = (String) array[0];

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<WalletDTO> exchange = null;

        if (scope.equals("role_trader")) {
            url = url + "/trader/id";
            exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, WalletDTO.class);
        }
        if (scope.equals("role_investor")) {
            url = url + "/investor/id";
            exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, WalletDTO.class);
        }
        if (exchange == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (exchange.getStatusCode().is2xxSuccessful()) {
            if (exchange.getBody().getCurrentBalance() > dto.getAmount()) {
                try {
                    String transactionId = eholeTransactionService.saveInvestTransaction(scope, dto.getAmount(), dto.getEholeId(), Long.valueOf(customPrincipal.getId()), dto.getEholeTransactionId());

                    Map<String, String> walletObject = new HashMap<>();
                    walletObject.put("paymentTransactionId", transactionId);
                    walletObject.put("amount", String.valueOf(dto.getAmount()));
                    walletObject.put("mathEnum", "MINUS");
                    HttpEntity<Map<String, String>> request = new HttpEntity<>(walletObject, headers);

                    return controllerHelper.updateWallet(scope, request);

                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity(exchange.getStatusCode());
        }

    }

    @PostMapping(path = "/payback")
    public ResponseEntity cancelPayback(@RequestBody PaybackDTO paybackDTO, OAuth2Authentication authentication) {
        List<PaybackTransactionDTO> paybackTransactionDTOS = paybackDTO.getPaybackTransactionDTOS();
        System.out.println("PAYBACK CALLED " + paybackTransactionDTOS.size());
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String accessToken = details.getTokenValue();

        if (paybackTransactionDTOS.size() == 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        for (PaybackTransactionDTO paybackTransactionDTO :
                paybackTransactionDTOS) {
            if (!eholeTransactionService.isTransactionExists(paybackTransactionDTO.getId(), paybackTransactionDTO.getUserType())){
                System.out.println("TRANSACTION MISMATCH");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        for (PaybackTransactionDTO paybackTransactionDTO :
                paybackTransactionDTOS) {
            String transactionId = eholeTransactionService.paybackTransactions(paybackTransactionDTO);
            if (transactionId != null) {
                String url = urlPropertyBundle.getWaletUrl();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + accessToken);
                headers.add("Content-Type", "application/json");
                HttpEntity httpEntity = new HttpEntity(headers);
                ResponseEntity<String> exchange = null;

                Map<String, String> walletObject = new HashMap<>();
                walletObject.put("paymentTransactionId", transactionId);
                walletObject.put("id", String.valueOf(paybackTransactionDTO.getUserId()));
                if (paybackDTO.getProfitMargin() != null && paybackDTO.getProfitMargin() != 0) {
                    System.out.println("IMPORTANT " +String.valueOf((paybackTransactionDTO.getAmount() + (paybackTransactionDTO.getAmount()
                            * paybackDTO.getProfitMargin()/100))));
                    walletObject.put("amount", String.valueOf((paybackTransactionDTO.getAmount() + (paybackTransactionDTO.getAmount()
                            * paybackDTO.getProfitMargin()/100))));
                } else {
                    walletObject.put("amount", String.valueOf(paybackTransactionDTO.getAmount()));
                }
                walletObject.put("mathEnum", "PLUS");
                HttpEntity<Map<String, String>> request = new HttpEntity<>(walletObject, headers);

                if (paybackTransactionDTO.getUserType().equals(UserType.INVESTOR)) {
                    url = urlPropertyBundle.getWaletUrl() + "/investor/update/id";

                } else if (paybackTransactionDTO.getUserType().equals(UserType.TRADER)) {
                    url = urlPropertyBundle.getWaletUrl() + "/trader/update/id";
                }
                ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
                if (response.getStatusCode().is2xxSuccessful()) {
//                    return new ResponseEntity<String>("Success", HttpStatus.OK);
                } else {
                    System.out.println("Unable to upload wallet");
                    return new ResponseEntity(response.getStatusCode());
                }

            } else {
                System.out.println("NO ENTITY EXISTS");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Ok", HttpStatus.OK);
    }


}
