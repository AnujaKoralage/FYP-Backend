package com.fyp.eholeservice.eholeservice.Controller;

import com.fyp.eholeservice.eholeservice.Component.ControllerHelper;
import com.fyp.eholeservice.eholeservice.DTO.EholeDTO;
import com.fyp.eholeservice.eholeservice.Enums.EholeType;
import com.fyp.eholeservice.eholeservice.Service.EholeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/ehole")
public class EholeController {

    @Autowired
    EholeService eholeService;

    @Autowired
    ControllerHelper controllerHelper;

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

}
