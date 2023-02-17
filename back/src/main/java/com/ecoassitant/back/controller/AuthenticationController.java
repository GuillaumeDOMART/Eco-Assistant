package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.*;
import com.ecoassitant.back.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Class to manage endpoints regarding authentication
 */
@RequestMapping("api/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Function to register a user
     *
     * @param registerInputDto input that represent the profile to create
     * @return Token authentication
     */
    @PostMapping("register")
    public ResponseEntity<TokenDto> register(@RequestBody RegisterInputDto registerInputDto) {
        return authenticationService.register(registerInputDto);
    }

    /**
     * Function to logged user
     *
     * @param authenticationInputDto input that represent the login form
     * @return Token authentication and the mail
     */
    @PostMapping("authentication")
    public AuthenticationOutPutDto authentication(@RequestBody AuthenticationInputDto authenticationInputDto) {
        return authenticationService.authentication(authenticationInputDto);
    }

    @GetMapping("guest")
    public ResponseEntity<TokenDto> guest() {
        return authenticationService.guest();
    }

    @PatchMapping("forgotMail")
    public boolean forgotMail(@RequestBody ForgotMailInput forgotMailInput){
        return authenticationService.forgotMail(forgotMailInput.getMail());
    }

}