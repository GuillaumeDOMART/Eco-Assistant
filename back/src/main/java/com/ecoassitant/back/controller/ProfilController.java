package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.ProfilDto;
import com.ecoassitant.back.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/")
@RestController
public class ProfilController {
    private final ProfilRepository repository;

    @Autowired
    public  ProfilController(ProfilRepository repository){
        this.repository = repository;
    }

    @GetMapping("/profil/{id}")
    @ResponseBody
    public ResponseEntity<ProfilDto> recupererProfilAvecId(@PathVariable("id") Long id){
        var profil = repository.findById(id).map(ProfilDto::new);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (profil.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profil.get(), headers, HttpStatus.OK);
        }
    }
}
