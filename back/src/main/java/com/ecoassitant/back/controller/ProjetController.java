package com.ecoassitant.back.controller;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.project.ProjectIdDto;
import com.ecoassitant.back.dto.project.ProjetDto;
import com.ecoassitant.back.dto.project.ProjetSimpleDto;
import com.ecoassitant.back.entity.ProjetEntity;
import com.ecoassitant.back.entity.tools.Etat;
import com.ecoassitant.back.entity.tools.TypeP;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.service.ProjetService;
import com.ecoassitant.back.service.ReponseDonneesService;
import com.ecoassitant.back.utils.StringGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    private final ProjetService projetService;

    private final ReponseDonneesService reponseDonneesService;

    /**
     * Constructor for ProjetController
     *
     * @param projetRepository      ProjetRepository
     * @param jwtService            JwtService
     * @param profilRepository      ProfilRepository
     * @param reponseDonneesService Service
     */
    @Autowired
    public ProjetController(ProjetRepository projetRepository, JwtService jwtService, ProfilRepository profilRepository, ProjetService projetService, ReponseDonneesService reponseDonneesService) {
        this.projetRepository = Objects.requireNonNull(projetRepository);
        this.jwtService = Objects.requireNonNull(jwtService);
        this.profilRepository = Objects.requireNonNull(profilRepository);
        this.projetService = Objects.requireNonNull(projetService);
        this.reponseDonneesService = Objects.requireNonNull(reponseDonneesService);
    }

    /**
     * Function to generate a random string
     *
     * @param length the length of the random string
     * @return the random string
     */
    private static String generateRandomString(int length) {
        return StringGeneratorUtils.generateRandomString(length);
    }

    /**
     * Endpoint to retrieve all projects
     */
    @GetMapping("/projets")
    @ResponseBody
    public List<ProjetDto> listerLesProjets() {
        return projetRepository.findAll().stream().map(ProjetDto::new).toList();
    }

    /**
     * Endpoint to retrieve a projet by id
     */
    @GetMapping("/projet/{id}")
    @ResponseBody
    public ResponseEntity<ProjetDto> recupererProjetAvecId(@PathVariable("id") Integer id) {
        var entity = projetRepository.findById(id);
        var dto = entity.map(ProjetDto::new).orElse(null);
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to retrieve a project by it's user's token
     */
    @GetMapping("/projet/user")
    @ResponseBody
    public ResponseEntity<List<ProjetDto>> recupererProjetAvecToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        return ResponseEntity.ok(projetRepository.findByProfilMail(mail).stream().map(ProjetDto::new).toList());
    }



    /**
     * Endpoint to create a project
     *
     * @param authorizationHeader the token of the user
     * @param projet              the project name
     * @return the project id
     */
    @PostMapping("/projet/create")
    public ResponseEntity<ProjectIdDto> createProject(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjetSimpleDto projet) {
        if (projet.getNom().length() >= 50) {
            throw new IllegalArgumentException("Le nom du projet ne peut pas avoir un nom de plus de 50 caractères");
        }
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var profilEntityOptional = profilRepository.findByMail(mail);
        if (profilEntityOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        var profil = profilEntityOptional.get();
        var projetEntity = ProjetEntity.builder()
                .nomProjet(projet.getNom())
                .profil(profil)
                .etat(Etat.INPROGRESS)
                .type(projet.getType().equals("simulation") ? TypeP.SIMULATION : TypeP.PROJET)
                .build();
        projetRepository.save(projetEntity);
        return new ResponseEntity<>(new ProjectIdDto(projetEntity.getIdProjet()), HttpStatus.OK);
    }

    /**
     * Method to dissociate a project from a user and associate it to a default anonymous
     *
     * @param authorizationHeader the token of the user
     * @param projetDto           the project name
     * @return the project dto id
     */
    @PutMapping("/projet/delete")
    public ResponseEntity<ProjectIdDto> delete(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjectIdDto projetDto) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var profilEntityOptional = profilRepository.findByMail(mail);
        if (profilEntityOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        var projet = projetRepository.findByIdProjet(projetDto.getId());
        var mailOwner = projet.getProfil().getMail();
        if (!mailOwner.equals(mail)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        var anoProfilOptional = profilRepository.findByMail("anonyme@demo.fr");
        if (anoProfilOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        projet.setNomProjet(generateRandomString(8));
        projet.setProfil(anoProfilOptional.get());
        projetRepository.save(projet);
        return new ResponseEntity<>(new ProjectIdDto(projet.getIdProjet()), HttpStatus.OK);

    }

    /**
     * Method to finish a project of a user
     *
     * @param authorizationHeader the token of the user
     * @param projetDto           the project name
     * @return the project dto id
     */
    @PutMapping("/projet/finish")
    public ResponseEntity<ProjectIdDto> finish(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjectIdDto projetDto) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var optionalProjet = projetService.finish(mail, projetDto);
        if (optionalProjet.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            var projet = optionalProjet.get();
            return new ResponseEntity<>(new ProjectIdDto(projet.getId()), HttpStatus.OK);
        }

    }

    /**
     * Method to dissociate a project from a user and associate it to a default anonymous
     *
     * @param authorizationHeader the token of the user
     * @param projectIdDto        the projectDTO
     * @return the project dto id
     */
    @PostMapping("/projet/{id}/copy")
    public ResponseEntity<ProjetDto> copy(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjectIdDto projectIdDto) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var projet = projetRepository.findByIdProjet(projectIdDto.getId());
        var mailOwner = projet.getProfil().getMail();
        var profil = profilRepository.findByMail(mail);

        // if token is not authorized in general or for this project
        if (profil.isEmpty() || !mailOwner.equals(mail)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        var projetEntity = ProjetEntity.builder()
                .nomProjet(projectIdDto.getProjectName())
                .profil(profil.get())
                .etat(Etat.INPROGRESS)
                .type(projectIdDto.getProjectType())
                .build();

        System.out.println("projetEntity = " + projetEntity);

        var projetCopy = projetService.save(projetEntity).orElseThrow();

        System.out.println("projetCopy = " + projetCopy);

        var answers = reponseDonneesService.findReponsesByProject(projet);
        answers.forEach(answer -> answer.updateReponseDonneeProjectId(projetCopy));

        reponseDonneesService.saveResponseDonnees(answers);

        return new ResponseEntity<>(new ProjetDto(projetCopy), HttpStatus.OK);

    }

    /**
     * Method to handle DataIntegrityViolationException into an HttpStatus.BAD_REQUEST when a project name already exist
     * with the same profil id
     *
     * @param ex exception
     * @return Map with the field nom and the message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class, IllegalArgumentException.class})
    @ResponseBody
    public Map<String, Map<String, String>> handleDataViolationExceptions(RuntimeException ex) {
        var message = ex.getMessage();
        if (ex.getMessage().contains("SQL"))
            message = "Le nom de projet est déjà utilisé, veuillez en choisir un autre";

        return Map.of("fieldErrors", Map.of("nom", message));
    }
}
