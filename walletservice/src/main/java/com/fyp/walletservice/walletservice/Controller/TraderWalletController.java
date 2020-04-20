package com.fyp.walletservice.walletservice.Controller;

import com.fyp.walletservice.walletservice.Components.UrlPropertyBundle;
import com.fyp.walletservice.walletservice.DTO.TraderWalletDTO;
import com.fyp.walletservice.walletservice.DTO.UsetDTO;
import com.fyp.walletservice.walletservice.Service.TraderService;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("api/v1/wallet/trader")
public class TraderWalletController {

    @Autowired
    TraderService traderService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @PostMapping(path = "/new")
    public ResponseEntity create(@RequestBody UsetDTO userDTO) {

        if (userDTO.getId() == null) {
            System.out.println("no id");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        TraderWalletDTO byTraderId = traderService.findByTraderId(userDTO.getId());
        if (byTraderId == null) {
            String url = urlPropertyBundle.getProfileServiceURL() + "/trader/id?username=" + userDTO.getUsername();
            ResponseEntity<Long> exchange = restTemplate.exchange(url, HttpMethod.GET, null, Long.class);
            if (exchange.getStatusCode().is2xxSuccessful()) {
                    if (exchange.getBody() != userDTO.getId()) {
                        System.out.println("Conflict on username and id");
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
                    }
            } else {
                System.out.println("Response Entity status not 200");
                return new ResponseEntity(exchange.getStatusCode());
            }

            TraderWalletDTO traderWalletDTO = new TraderWalletDTO();
            traderWalletDTO.setTraderId(userDTO.getId());
            traderWalletDTO.setCreatedDate(LocalDate.now());
            traderWalletDTO.setLastUpateedDate(LocalDate.now());
            traderWalletDTO.setCurrentBalance(0.00);

            try {
                TraderWalletDTO traderWalletDTOsaved = traderService.create(traderWalletDTO);
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/test")
    public ResponseEntity test(){
        return new ResponseEntity<String>("OK FINE", HttpStatus.OK);
    }

}
