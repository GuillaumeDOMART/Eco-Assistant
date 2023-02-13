package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.ProfilDto;
import com.ecoassitant.back.dto.ProfilSimplDto;
import com.ecoassitant.back.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class to manage endpoints regarding profiles
 */
@RequestMapping("api/")
@RestController
public class ProfilController {
    private final ProfilService profilService;

    @Autowired
    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }


    /**
     * Endpoint to retrieve profil by id
     */
    @GetMapping("/profil/{id}")
    @ResponseBody
    public ResponseEntity<ProfilDto> recupererProfilAvecId(@PathVariable("id") Long id) {
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
    @GetMapping("/profil/search/{mail}")
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

    @PostMapping("/profil")
    public ResponseEntity<Long> createProfil(@RequestBody ProfilSimplDto profilDto){
        var id = profilService.createProfil(profilDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}