package com.fyp.walletservice.walletservice.Controller;

import com.fyp.walletservice.walletservice.Components.UrlPropertyBundle;
import com.fyp.walletservice.walletservice.DTO.InvestorWalletDTO;
import com.fyp.walletservice.walletservice.DTO.InvestorWalletTransactionDTO;
import com.fyp.walletservice.walletservice.DTO.TraderWalletTransactionDTO;
import com.fyp.walletservice.walletservice.DTO.UsetDTO;
import com.fyp.walletservice.walletservice.Enums.MathEnum;
import com.fyp.walletservice.walletservice.Service.InvestorService;
import com.fyp.walletservice.walletservice.Utils.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("api/v1/wallet/investor")

public class InvestorController {
    @Autowired
    InvestorService investorService;

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
        InvestorWalletDTO byInvestorId = investorService.findByInvestorId(userDTO.getId());
        if (byInvestorId == null) {
            String url = urlPropertyBundle.getProfileServiceURL() + "/investor/id?username=" + userDTO.getUsername();
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

            InvestorWalletDTO InvestorWalletDTO = new InvestorWalletDTO();
            InvestorWalletDTO.setInvestorId(userDTO.getId());
            InvestorWalletDTO.setCreatedDate(LocalDate.now());
            InvestorWalletDTO.setLastUpateedDate(LocalDate.now());
            InvestorWalletDTO.setCurrentBalance(0.00);

            try {
                InvestorWalletDTO InvestorWalletDTOsaved = investorService.create(InvestorWalletDTO);
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

    /*
     * for ui data requirements
     * */
    @GetMapping(path = "/id")
    public ResponseEntity getUserWalletDetailsByUser(Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        try {
            InvestorWalletDTO walletById = investorService.findWalletById(Long.valueOf(principal.getId()));
            walletById.setLastUpateedDate(null);
            walletById.setCreatedDate(null);
            walletById.setId(null);
            return new ResponseEntity<InvestorWalletDTO>(walletById, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity updateUserWallet(@RequestBody InvestorWalletTransactionDTO request, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        double balace;
        try {
            InvestorWalletDTO walletById = investorService.findWalletById(Long.valueOf(principal.getId()));
            if (request.getMathEnum() == MathEnum.MINUS) {
                balace = walletById.getCurrentBalance() - request.getAmount();
                if (balace < 0) {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            } else {
                balace = walletById.getCurrentBalance() + request.getAmount();
            }
            InvestorWalletTransactionDTO update = investorService.update(request, balace, Long.valueOf(principal.getId()));
            return new ResponseEntity<InvestorWalletTransactionDTO>(update, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/id")
    public ResponseEntity updateUserwalletById(@RequestBody InvestorWalletTransactionDTO request) {
        try {
            InvestorWalletDTO walletById = investorService.findWalletById(request.getId());
            Double newBalance = walletById.getCurrentBalance() + request.getAmount();
            InvestorWalletTransactionDTO update = investorService.update(request, newBalance, request.getId());
            if (update != null) {
                return new ResponseEntity(HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
