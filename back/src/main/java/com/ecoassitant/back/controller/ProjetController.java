package com.ecoassitant.back.controller;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.ProjectIdDto;
import com.ecoassitant.back.dto.ProjetDto;
import com.ecoassitant.back.dto.ProjetSimpleDto;
import com.ecoassitant.back.entity.ProjetEntity;
import com.ecoassitant.back.entity.tools.Etat;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final JwtService jwtService;
    private final ProfilRepository profilRepository;

    /**
     * Constructor for ProjetController
     * @param projetRepository ProjetRepository
     * @param jwtService JwtService
     * @param profilRepository ProfilRepository
     */
    @Autowired
    public ProjetController(ProjetRepository projetRepository, JwtService jwtService, ProfilRepository profilRepository) {
        this.projetRepository = Objects.requireNonNull(projetRepository);
        this.jwtService = Objects.requireNonNull(jwtService);
        this.profilRepository = Objects.requireNonNull(profilRepository);
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
    public ResponseEntity<ProjetDto> recupererProjetAvecId(@PathVariable("id") Integer id){
        var entity = projetRepository.findById(id);
        var dto = entity.map(ProjetDto::new).orElse(null);
        return dto!= null? new ResponseEntity<>(dto, HttpStatus.OK): new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to retrieve a projet by it's user's token
     */
    @GetMapping("/projet/user")
    @ResponseBody
    public ResponseEntity<List<ProjetDto>> recupererProjetAvecToken(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        return ResponseEntity.ok(projetRepository.findByProfilMail(mail).stream().map(ProjetDto::new).toList());
    }

    /**
     * Endpoint to create a project
     * @param authorizationHeader the token of the user
     * @param projet the project name
     * @return the project id
     */
    @PostMapping("/projet/create")
    public ResponseEntity<ProjectIdDto> createProject(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjetSimpleDto projet){
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var profilEntityOptional = profilRepository.findByMail(mail);
        if(profilEntityOptional.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        var profil = profilEntityOptional.get();
        var projetEntity = ProjetEntity.builder()
                .nomProjet(projet.getNom())
                .profil(profil)
                .etat(Etat.INPROGRESS)
                .build();
        projetRepository.save(projetEntity);
        return new ResponseEntity<>(new ProjectIdDto(projetEntity.getIdProjet()), HttpStatus.OK);
    }

}
