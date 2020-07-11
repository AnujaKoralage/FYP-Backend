package com.fyp.profileservice.profile.Controller;

import com.fyp.profileservice.profile.Component.UrlPropertyBundle;
import com.fyp.profileservice.profile.Component.ValidationMessageBundle;
import com.fyp.profileservice.profile.DTO.UserDTO;
import com.fyp.profileservice.profile.Enum.UserRoleEnum;
import com.fyp.profileservice.profile.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/v1/profile/investor")
public class InvestorController {
    @Autowired
    UserService userService;

    @Autowired
    ValidationMessageBundle validationMessageBundle;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/register")
    public ResponseEntity create(@Valid @RequestBody UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getPassword() == null || userDTO.getUsername() == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        userDTO.setUserRoleEnum(UserRoleEnum.ROLE_INVESTOR);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        try {
            UserDTO savedUser = userService.save(userDTO);
            savedUser.setPassword(null);
            String url = urlPropertyBundle.getWalletServiceUrl() + "/investor/new";
            HttpEntity<UserDTO> userDTORequestEntity = new HttpEntity<UserDTO>(savedUser);
            ResponseEntity<HttpStatus> exchange = restTemplate.exchange(url, HttpMethod.POST, userDTORequestEntity, HttpStatus.class);
            if (exchange.getStatusCode().is2xxSuccessful()){
                return new ResponseEntity<UserDTO>(savedUser, HttpStatus.CREATED);
            }
            else {
                System.out.println("wallet creation failed");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("User exists")){
                return new ResponseEntity<String>(validationMessageBundle.getUserAlreadyExist(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>(validationMessageBundle.getUserCreationFaild(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/name")
    public ResponseEntity getUserByUsername(@RequestParam(name = "username") String name){
        if (name != null){
            UserDTO userByUsername = userService.getUserByUsername(name);
            if (userByUsername == null){
                return new ResponseEntity(true ,HttpStatus.OK);
            }
            return new ResponseEntity(false, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "/id")
    public ResponseEntity getUserId(@RequestParam(name = "username") String name){
        if (name != null){
            Long userIdByUsername = userService.getUserIdByUsername(name, UserRoleEnum.ROLE_INVESTOR);
            if (userIdByUsername != null) {
                return new ResponseEntity<Long>(userIdByUsername, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/test")
    public ResponseEntity test() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/api/v1/twallet/test", HttpMethod.GET, null, String.class);
        return exchange;
    }

}
