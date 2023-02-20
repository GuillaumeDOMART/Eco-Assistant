package com.ecoassitant.back.controller;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.ProfilDto;
import com.ecoassitant.back.dto.ProfilSimplDto;
import com.ecoassitant.back.service.ProfilService;
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

    /**
     * Constructor of ProfilController
     * @param jwtService for decipher the token
     * @param profilService Service of Profil
     */
    @Autowired
    public ProfilController(JwtService jwtService, ProfilService profilService) {
        this.jwtService = Objects.requireNonNull(jwtService);
        this.profilService = Objects.requireNonNull(profilService);
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

    @PostMapping("profil")
    public ResponseEntity<Integer> createProfil(@RequestBody ProfilSimplDto profilDto){
        var id = profilService.createProfil(profilDto);
        System.out.println("j'ai id");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a profil by it's user's token
     */
    @GetMapping("/profil/user")
    @ResponseBody
    public ResponseEntity<ProfilDto> recupererProfilAvecToken(@RequestHeader("Authorization") String authorizationHeader){
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
}