package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.ProjetDto;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class ProjetController {
    private final ProjetService projetService;

    @Autowired
    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @GetMapping("/projets")
    @ResponseBody
    public ResponseEntity<List<ProjetDto>> listerLesProjets(){
        List<ProjetDto> projets = projetService.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(projets.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(projets,headers,HttpStatus.OK);
        }
    }

    @GetMapping("/projet/{id}")
    @ResponseBody
    public ResponseEntity<ProjetDto> recupererProjetAvecId(@PathVariable("id") Long id){
        ProjetDto projet = projetService.getProject(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(projet == null){
            return new ResponseEntity<>(projet,headers,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(projet,headers,HttpStatus.OK);
        }
    }
    @GetMapping("/projet/user/{id}")
    @ResponseBody
    public ResponseEntity<List<ProjetDto>> recupererProjetAvecUserId(@PathVariable("id") Long id){
        List<ProjetDto> projets = projetService.findProjectByProfilId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(projets.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(projets,headers,HttpStatus.OK);
        }
    }
    //TODO Dissocier un projet return projet dissocié avec 204

}
