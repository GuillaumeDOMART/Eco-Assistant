package com.ecoassitant.back.controller;

import com.ecoassitant.back.entity.ProjetEntity;
import com.ecoassitant.back.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api")
@RestController
public class ProjetController {
    private final ProjetRepository projetRepository;

    @Autowired
    public ProjetController(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    @GetMapping("/projets")
    @ResponseBody
    public List<ProjetEntity> listerLesProjets(){
        return projetRepository.findAll();
    }

    @GetMapping("/projet/{id}")
    @ResponseBody
    public Optional<ProjetEntity> recupererProjetAvecId(@PathVariable("id") Long id){
        return projetRepository.findById(id);
    }
}
