package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.*;
import com.ecoassitant.back.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Class to manage endpoints regarding authentication
 */
@RequestMapping("api/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    /**
     * Constructor for AuthenticationController
     *
     * @param authenticationService AuthenticationService
     */
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = Objects.requireNonNull(authenticationService);
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

    /**
     * Function to create guest profile
     *
     * @return the token of guest profile
     */
    @GetMapping("guest")
    public ResponseEntity<TokenDto> guest() {
        return authenticationService.guest();
    }

    /**
     * Function to send email when the user forgot his password
     *
     * @param forgotMailInput the mail of the user
     * @return
     */
    @PatchMapping("forgotMail")
    public boolean forgotMail(@RequestBody ForgotMailInput forgotMailInput) {
        return authenticationService.forgotMail(forgotMailInput.getMail());
    }

    /**
     * Method to change the mail of the logged user
     *
     * @param authorizationToken Authorization token of the logged user
     * @param newMail            The new mail of the user
     * @return True if the mail has been modified, false otherwise
     */
    @PatchMapping("changeMail")
    public ResponseEntity<TokenDto> changeMail(@RequestHeader("Authorization") String authorizationToken, @RequestBody String newMail) {
        var token = authorizationToken.substring(7);
        var mail = newMail.substring(12, newMail.length() - 2);
        return authenticationService.changeMail(token, mail);
    }

    /**
     * Method to change the password of the logged user
     *
     * @param authorizationToken Authorization token of the logged user
     * @param jsonString         Json containing the password
     * @return True if the mail has been modified, false otherwise
     */

    @PatchMapping("changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestHeader("Authorization") String authorizationToken, @RequestBody String jsonString) {
        var token = authorizationToken.substring(7);
        var pwd = jsonString.substring(16, jsonString.length() - 2);
        System.out.println("jsonString = " + jsonString);
        System.out.println("pwd = " + pwd);
        return authenticationService.changePassword(token, pwd);
    }

}