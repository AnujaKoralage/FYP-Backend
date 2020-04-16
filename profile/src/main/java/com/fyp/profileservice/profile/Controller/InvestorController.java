package com.fyp.profileservice.profile.Controller;

import com.fyp.profileservice.profile.Component.ValidationMessageBundle;
import com.fyp.profileservice.profile.DTO.UserDTO;
import com.fyp.profileservice.profile.Enum.UserRoleEnum;
import com.fyp.profileservice.profile.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/v1/investor")
public class InvestorController {
    @Autowired
    UserService userService;

    @Autowired
    ValidationMessageBundle validationMessageBundle;

    @Autowired
    PasswordEncoder passwordEncoder;

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
            return new ResponseEntity<UserDTO>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
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
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.FOUND);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "/id")
    public ResponseEntity getUserId(@RequestParam(name = "username") String name){
        if (name != null){
            Long userIdByUsername = userService.getUserIdByUsername(name, UserRoleEnum.ROLE_INVESTOR);
            if (userIdByUsername != null) {
                return new ResponseEntity<Long>(userIdByUsername, HttpStatus.FOUND);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
