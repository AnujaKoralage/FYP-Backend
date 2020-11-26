package com.fyp.eholeservice.eholeservice.Controller;

import com.fyp.eholeservice.eholeservice.Component.ControllerHelper;
import com.fyp.eholeservice.eholeservice.Component.UrlPropertyBundle;
import com.fyp.eholeservice.eholeservice.DTO.*;
import com.fyp.eholeservice.eholeservice.Entity.EholeTransactionEntity;
import com.fyp.eholeservice.eholeservice.Enums.EholeStatusType;
import com.fyp.eholeservice.eholeservice.Enums.EholeType;
import com.fyp.eholeservice.eholeservice.Enums.TransactionType;
import com.fyp.eholeservice.eholeservice.Enums.UserType;
import com.fyp.eholeservice.eholeservice.Service.EholeService;
import com.fyp.eholeservice.eholeservice.Util.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/ehole")
public class EholeController {

    @Autowired
    EholeService eholeService;

    @Autowired
    ControllerHelper controllerHelper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @PostMapping(path = "/create")
    public ResponseEntity create(@RequestBody EholeDTO eholeDTO, OAuth2Authentication authentication) {

        if (eholeDTO.getCompletedAmount() == 0 || !EholeType.contains(eholeDTO.getEholeType())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!controllerHelper.getScope(authentication).equals("role_trader")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        try {
            Long eholeId = eholeService.createEhole(eholeDTO, Long.valueOf(controllerHelper.getCustomPrincipal(authentication).getId()));
            return new ResponseEntity(eholeId ,HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/cancel")
    public ResponseEntity cancelEhole(@RequestParam Long id, OAuth2Authentication authentication) {
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String accessToken = details.getTokenValue();

        try {
            if (eholeService.isEholeCreaterId(id, Long.valueOf(controllerHelper.getCustomPrincipal(authentication).getId()))){
                eholeService.updateEholeStatus(id, EholeStatusType.CANCELED);
                ArrayList<PaybackTransactionDTO> paybackTransactionDTOS = eholeService.paybackTransactions(id, TransactionType.PAYBACK);
                System.out.println("LENGTH _ " + paybackTransactionDTOS.size());
                String url = urlPropertyBundle.getEholePaymentUrl() + "/invest/payback";
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + accessToken);
                headers.add("Content-Type", "application/json");
                PaybackDTO paybackDTO = new PaybackDTO();
                paybackDTO.setPaybackTransactionDTOS(paybackTransactionDTOS);
                paybackDTO.setProfitMargin(null);
                HttpEntity<PaybackDTO> request = new HttpEntity<>(paybackDTO, headers);

                ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
                return new ResponseEntity(exchange.getStatusCode());

//                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path = "/invest")
    public ResponseEntity investOnEhole(@RequestBody InvestEholeDTO investEholeDTO, OAuth2Authentication authentication) {

        String scope = controllerHelper.getScope(authentication);
        CustomPrincipal customPrincipal = controllerHelper.getCustomPrincipal(authentication);
        String accessToken = controllerHelper.getAccessToken(authentication);
        String url = urlPropertyBundle.getWaletUrl();
        UserType userType = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<WalletDTO> exchange = null;
        if (!eholeService.isEholeExists(investEholeDTO.getEholeId())) {
            System.out.println("NO KIND OF EHOLE!!");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (scope.equals("role_trader")) {
            url = url + "/trader/id";
            userType = UserType.TRADER;
            exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, WalletDTO.class);
        }
        if (scope.equals("role_investor")) {
            url = url + "/investor/id";
            userType = UserType.INVESTOR;
            exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, WalletDTO.class);
        }

        if (exchange == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            if (exchange.getStatusCode().is2xxSuccessful()) {
                if (Objects.requireNonNull(exchange.getBody()).getCurrentBalance() < investEholeDTO.getAmount()) {
                    System.out.println("ALREADY FULLED");
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                } else {
                    EholeTransactionEntity isInvested = null;
                    try {
                        isInvested = eholeService.investEhole(investEholeDTO.getEholeId(), investEholeDTO.getAmount(), Long.valueOf(customPrincipal.getId()), userType);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    if (isInvested != null) {
                        Map<String, String> investObject = new HashMap<>();
                        investObject.put("eholeId", String.valueOf(investEholeDTO.getEholeId()));
                        investObject.put("amount", String.valueOf(investEholeDTO.getAmount()));
                        investObject.put("eholeTransactionId", String.valueOf(isInvested.getId()));
                        HttpEntity<Map<String, String>> request = new HttpEntity<>(investObject, headers);

                        return controllerHelper.saveInvestEholeOnPaymentService(request);
//                        return new ResponseEntity(HttpStatus.OK);
                    } else {
                        System.out.println("IS INVEST FALSE");
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                System.out.println("EXCHANGE CODE FAILED");
                return new ResponseEntity(exchange.getStatusCode());
            }
        }

    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity getEholeById(@PathVariable("id") long id) {

        EholeDTO eholeById = eholeService.getEholeById(id);
        if (eholeById == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new <EholeDTO>ResponseEntity(eholeById, HttpStatus.OK);
    }

    @GetMapping(path = "/active/{status}")
    public ResponseEntity getEholeByStatus(@PathVariable("status") EholeStatusType status, OAuth2Authentication authentication) {
        CustomPrincipal customPrincipal = controllerHelper.getCustomPrincipal(authentication);

        List<EhDTO> eholeByStatus = eholeService.getEholeByStatus(status);
        if (status == EholeStatusType.TRADING) {
            List<EhDTO> removingArr = new ArrayList<>();
            eholeByStatus.forEach(ehDTO -> {
                if ( !customPrincipal.getId().equals(ehDTO.getTraderId().toString())) {
                    removingArr.add(ehDTO);
                }
            });
            eholeByStatus.removeAll(removingArr);
        }

        return new ResponseEntity(eholeByStatus, HttpStatus.OK);
    }

    @GetMapping(path = "/auth/{id}")
    public ResponseEntity checkEholeAuth(OAuth2Authentication authentication, @PathVariable("id") long id) {
        CustomPrincipal customPrincipal = controllerHelper.getCustomPrincipal(authentication);
        boolean b = eholeService.checkEholeAuth(id, Long.parseLong(customPrincipal.getId()));
        if (b) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/end/{id}")
    public ResponseEntity endEhole(OAuth2Authentication authentication, @PathVariable("id") long id) {
        CustomPrincipal customPrincipal = controllerHelper.getCustomPrincipal(authentication);
        String accessToken = controllerHelper.getAccessToken(authentication);
        EholeDTO eholeById = eholeService.getEholeById(id);
        boolean b = eholeService.endEhole(id, Long.parseLong(customPrincipal.getId()));
        if (!b) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ArrayList<PaybackTransactionDTO> paybackTransactionDTOS = eholeService.paybackTransactions(id, TransactionType.FINISH);
        System.out.println("LENGTH _ " + paybackTransactionDTOS.size());
        String url = urlPropertyBundle.getEholePaymentUrl() + "/invest/payback";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        PaybackDTO paybackDTO = new PaybackDTO();
        paybackDTO.setPaybackTransactionDTOS(paybackTransactionDTOS);
        paybackDTO.setProfitMargin(eholeById.getProfit());
        HttpEntity<PaybackDTO> request = new HttpEntity<>(paybackDTO, headers);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return new ResponseEntity("!@", exchange.getStatusCode());
    }

    @GetMapping(path = "/transactions")
    public ResponseEntity getUserTransactions(OAuth2Authentication authentication) {
        CustomPrincipal customPrincipal = controllerHelper.getCustomPrincipal(authentication);

        List<EholeTransactionDTO> userTransactions = eholeService.getUserTransactions(Long.valueOf(customPrincipal.getId()));
        return new ResponseEntity(userTransactions, HttpStatus.OK);
    }
}
