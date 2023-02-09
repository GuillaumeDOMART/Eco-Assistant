package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.ProjetDto;
import com.ecoassitant.back.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Class to manage endpoints regarding profiles
 */
@RequestMapping("api")
@RestController
public class ProjetController {
    private final ProjetRepository projetRepository;

    @Autowired
    public ProjetController(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    /**
     * Endpoint to retrieve all projects
     */
    @GetMapping("/projets")
    @ResponseBody
    public List<ProjetDto> listerLesProjets(){
        return projetRepository.findAll().stream().map(ProjetDto::new).toList();
    }

    /**
     * Endpoint to retrieve a projet by id
     */
    @GetMapping("/projet/{id}")
    @ResponseBody
    public ProjetDto recupererProjetAvecId(@PathVariable("id") Long id){
        var entity = projetRepository.findById(id);
        return entity.map(ProjetDto::new).orElse(null);
    }

    /**
     * Endpoint to retrieve a projet by it's user's id
     */
    @GetMapping("/projet/user/{id}")
    @ResponseBody
    public List<ProjetDto> recupererProjetAvecUserId(@PathVariable("id") Long id){
        return projetRepository.findAll().stream().filter(e -> Objects.equals(e.getProfil().getIdProfil(), id)).map(ProjetDto::new).toList();
    }
}
