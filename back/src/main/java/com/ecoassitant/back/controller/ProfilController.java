package com.ecoassitant.back.controller;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.*;
import com.ecoassitant.back.service.ProfilService;
import com.ecoassitant.back.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Class to manage endpoints regarding profiles
 */
@RequestMapping("api/")
@RestController
public class ProfilController {
    private final JwtService jwtService;
    private final ProfilService profilService;
    private final AuthenticationService authenticationService;

    /**
     * Constructor of ProfilController
     *
     * @param jwtService            for decipher the token
     * @param profilService         Service of Profil
     * @param authenticationService AuthenticationService
     */
    @Autowired
    public ProfilController(JwtService jwtService, ProfilService profilService, AuthenticationService authenticationService) {
        this.jwtService = Objects.requireNonNull(jwtService);
        this.profilService = Objects.requireNonNull(profilService);
        this.authenticationService = authenticationService;
    }


    /**
     * Endpoint to retrieve profil by id
     */
    @GetMapping("profil/{id}")
    @ResponseBody
    public ResponseEntity<ProfilDto> recupererProfilAvecId(@PathVariable("id") Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var profil = profilService.getProfilByID(id);
        if (profil == null) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profil, headers, HttpStatus.OK);
        }
    }

    /**
     * Endpoint to retrieve profil by mail
     */
    @GetMapping("profil/search/{mail}")
    @ResponseBody
    public ResponseEntity<ProfilDto> recupererProfilAvecMail(@PathVariable("mail") String mail) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var profil = profilService.getProfilByMail(mail);
        if (profil == null) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profil, headers, HttpStatus.OK);
        }
    }

    /**
     * Endpoint to create a user admin
     *
     * @param profilDto profile to create
     * @return return the id of the profile
     */
    @PostMapping("profil")
    public ResponseEntity<Integer> createProfil(@RequestBody ProfilSimplDto profilDto) {
        var id = profilService.createProfil(profilDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a profil by it's user's token
     */
    @GetMapping("/profil/user")
    @ResponseBody
    public ResponseEntity<ProfilDto> recupererProfilAvecToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var profil = profilService.getProfilByMail(mail);
        if (profil == null) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profil, headers, HttpStatus.OK);
        }
    }

    /**
     * Function to change password when the user click on the link on the email
     *
     * @param authorizationHeader the token of the mail
     * @return if the password was change successfully
     */
    @PatchMapping("/profil/forgotMail")
    public ResponseEntity<Boolean> forgotMail(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ForgotPasswordVerifyDto forgotPasswordVerifyDto) {
        String token = authorizationHeader.substring(7);
        if (!jwtService.extractVerify(token)) {
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
        var mail = jwtService.extractMail(token);
        return authenticationService.changePassword(mail, forgotPasswordVerifyDto.getPassword(), forgotPasswordVerifyDto.getOldPassword());
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
        if (profil.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profil.get(), HttpStatus.OK);
        }


    }

    @PatchMapping("profil/register")
    public ResponseEntity<Boolean> register(@RequestHeader("Authorization") String authorizationHeader) {
        return profilService.register(authorizationHeader.substring(7));
    }
}