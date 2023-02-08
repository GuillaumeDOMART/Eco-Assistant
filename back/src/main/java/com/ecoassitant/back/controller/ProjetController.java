package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.ProjetDto;
import com.ecoassitant.back.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ProjetDto> listerLesProjets(){
        return projetRepository.findAll().stream().map(ProjetDto::new).toList();
    }

    @GetMapping("/projet/{id}")
    @ResponseBody
    public ProjetDto recupererProjetAvecId(@PathVariable("id") Long id){
        var entity = projetRepository.findById(id);
        return entity.map(ProjetDto::new).orElse(null);
    }
}
