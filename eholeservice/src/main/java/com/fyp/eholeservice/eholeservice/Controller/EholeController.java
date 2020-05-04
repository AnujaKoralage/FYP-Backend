package com.fyp.eholeservice.eholeservice.Controller;

import com.fyp.eholeservice.eholeservice.Component.ControllerHelper;
import com.fyp.eholeservice.eholeservice.Component.UrlPropertyBundle;
import com.fyp.eholeservice.eholeservice.DTO.EholeDTO;
import com.fyp.eholeservice.eholeservice.DTO.InvestEholeDTO;
import com.fyp.eholeservice.eholeservice.DTO.WalletDTO;
import com.fyp.eholeservice.eholeservice.Enums.EholeStatusType;
import com.fyp.eholeservice.eholeservice.Enums.EholeType;
import com.fyp.eholeservice.eholeservice.Enums.UserType;
import com.fyp.eholeservice.eholeservice.Service.EholeService;
import com.fyp.eholeservice.eholeservice.Util.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

        if (eholeDTO.getAmount() == 0 || !EholeType.contains(eholeDTO.getEholeType())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!controllerHelper.getScope(authentication).equals("role_trader")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        try {
            eholeService.createEhole(eholeDTO, Long.valueOf(controllerHelper.getCustomPrincipal(authentication).getId()));
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/cancel")
    public ResponseEntity cancelEhole(@RequestParam Long id, OAuth2Authentication authentication) {

        try {
            if (eholeService.isEholeCreaterId(id, Long.valueOf(controllerHelper.getCustomPrincipal(authentication).getId()))){
                eholeService.updateEholeStatus(id, EholeStatusType.CANCELED);
                return new ResponseEntity(HttpStatus.OK);
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
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                } else {
                    boolean isInvested = false;
                    try {
                        isInvested = eholeService.investEhole(investEholeDTO.getEholeId(), investEholeDTO.getAmount(), Long.valueOf(customPrincipal.getId()), userType);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    if (isInvested) {
                        Map<String, String> investObject = new HashMap<>();
                        investObject.put("eholeId", String.valueOf(investEholeDTO.getEholeId()));
                        investObject.put("amount", String.valueOf(investEholeDTO.getAmount()));
                        HttpEntity<Map<String, String>> request = new HttpEntity<>(investObject, headers);

                        controllerHelper.saveInvestEholeOnPaymentService(request);

                        return new ResponseEntity(HttpStatus.OK);
                    } else {
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                return new ResponseEntity(exchange.getStatusCode());
            }
        }

    }

}
