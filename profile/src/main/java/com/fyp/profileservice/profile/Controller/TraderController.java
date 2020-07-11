package com.fyp.profileservice.profile.Controller;

import com.fyp.profileservice.profile.Component.UrlPropertyBundle;
import com.fyp.profileservice.profile.Component.ValidationMessageBundle;
import com.fyp.profileservice.profile.DTO.UserDTO;
import com.fyp.profileservice.profile.Enum.UserRoleEnum;
import com.fyp.profileservice.profile.Service.UserService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/v1/profile/trader")
public class TraderController {
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
        userDTO.setUserRoleEnum(UserRoleEnum.ROLE_TRADER);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        try {
            UserDTO savedUser = userService.save(userDTO);
            savedUser.setPassword(null);
            String url = urlPropertyBundle.getWalletServiceUrl() + "/trader/new";
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
            Long userIdByUsername = userService.getUserIdByUsername(name, UserRoleEnum.ROLE_TRADER);
            if (userIdByUsername != null) {
                return new ResponseEntity<Long>(userIdByUsername, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/idName/{id}")
    public ResponseEntity getUserName(@PathVariable(name = "id") String id) {
        String userName = userService.getUserName(Long.parseLong(id));
        return new ResponseEntity(userName, HttpStatus.OK);
    }
}
