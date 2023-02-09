package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.ProfilDto;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("api/")
@RestController
public class ProfilController {
    private final ProfilService profilService;

    @Autowired
    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }


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
}