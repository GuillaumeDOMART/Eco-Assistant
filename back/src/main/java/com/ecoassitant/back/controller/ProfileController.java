package com.ecoassitant.back.controller;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.*;
import com.ecoassitant.back.dto.profil.ProfilDto;
import com.ecoassitant.back.dto.profil.ProfilIdDto;
import com.ecoassitant.back.dto.profil.ProfilSimplDto;
import com.ecoassitant.back.service.ProfilService;
import com.ecoassitant.back.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Class to manage endpoints regarding profiles
 */
@RequestMapping("api/")
@RestController
public class ProfileController {
    private final JwtService jwtService;
    private final ProfilService profilService;
    private final AuthenticationService authenticationService;

    /**
     * Constructor of ProfileController
     *
     * @param jwtService            for decipher the token
     * @param profilService         Service of Profil
     * @param authenticationService AuthenticationService
     */
    @Autowired
    public ProfileController(JwtService jwtService, ProfilService profilService, AuthenticationService authenticationService) {
        this.jwtService = Objects.requireNonNull(jwtService);
        this.profilService = Objects.requireNonNull(profilService);
        this.authenticationService = authenticationService;
    }


    /**
     * Endpoint to retrieve profil by id
     */
    @GetMapping("profil/{id}")
    @ResponseBody
    public ResponseEntity<ProfilDto> recoverProfilWithId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profilService.recoverProfilWithId(id));
    }

    /**
     * Endpoint to retrieve profil by mail
     */
    @GetMapping("profil/search/{mail}")
    @ResponseBody
    public ResponseEntity<ProfilDto> recoverProfilWithMail(@PathVariable("mail") String mail) {
        return ResponseEntity.ok(profilService.recoverProfilWithMail(mail));
    }

    /**
     * Endpoint to create a user admin
     *
     * @param profilDto profile to create
     * @return return the id of the profile
     */
    @PostMapping("profil")
    public ResponseEntity<Integer> createProfil(@RequestBody ProfilSimplDto profilDto){
        return new ResponseEntity<>(profilService.createProfil(profilDto), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a profil by it's user's token
     */
    @GetMapping("/profil/user")
    @ResponseBody
    public ResponseEntity<ProfilDto> recoverProfilWithToken(@RequestHeader("Authorization") String authorizationHeader){
        return new ResponseEntity<>(profilService.recoverProfilWithToken(authorizationHeader), HttpStatus.OK);
    }

    /**
     * Function to change password when the user click on the link on the email
     *
     * @param authorizationHeader the token of the mail
     * @return if the password was change successfully
     */
    @PatchMapping("/profil/forgotMail")
    public ResponseEntity<Boolean> forgotMail(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ForgotPasswordVerifyDto forgotPasswordVerifyDto){
        return profilService.forgotMail(authorizationHeader, forgotPasswordVerifyDto);
    }

    /**
     * Function to delete the profile of the user currently connected
     *
     * @param authorizationHeader the token of the user
     * @return ResponseEntity of ProfilIdDto of profile deleted
     */
    @PutMapping("/profil/delete")
    public ResponseEntity<ProfilIdDto> deleteProfil(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var profil = profilService.deleteProfil(mail);
        return profil.map(profilIdDto -> new ResponseEntity<>(profilIdDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    /**
     * Function to finalize profile creation
     * @param authorizationHeader bearer token
     * @return if the account was created
     */
    @PatchMapping("profil/register")
    public ResponseEntity<Boolean> register(@RequestHeader("Authorization") String authorizationHeader) {
        return profilService.register(authorizationHeader.substring(7));
    }
    /**
     * Function to delete the profile of the user currently connected
     * @param authorizationHeader the token of the user
     * @return ResponseEntity of ProfilIdDto of profile deleted
     */
    @GetMapping("/profil/users")
    public ResponseEntity<List<ProfilDto>> getAllUsersProfil(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var profil = profilService.getAllUsersProfil(mail);
        if(profil.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(profil.get(), HttpStatus.OK);
        }

    }
}