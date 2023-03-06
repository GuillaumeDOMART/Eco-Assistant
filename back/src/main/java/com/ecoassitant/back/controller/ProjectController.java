package com.ecoassitant.back.controller;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.project.ProjectDto;
import com.ecoassitant.back.dto.project.ProjectIdDto;
import com.ecoassitant.back.dto.project.ProjectSimpleDto;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.tools.State;
import com.ecoassitant.back.entity.tools.TypeP;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjectRepository;
import com.ecoassitant.back.service.GivenAnswerService;
import com.ecoassitant.back.service.ProjectService;
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
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final JwtService jwtService;
    private final ProfilRepository profilRepository;
    private final ProjectService projetService;

    private final GivenAnswerService reponseDonneesService;

    /**
     * Constructor for ProjectController
     *
     * @param projectRepository      ProjectRepository
     * @param jwtService            JwtService
     * @param profilRepository      ProfilRepository
     * @param givenAnswerService Service
     */
    @Autowired
    public ProjectController(ProjectRepository projectRepository, JwtService jwtService, ProfilRepository profilRepository, ProjectService projectService, GivenAnswerService givenAnswerService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.jwtService = Objects.requireNonNull(jwtService);
        this.profilRepository = Objects.requireNonNull(profilRepository);
        this.projetService = Objects.requireNonNull(projectService);
        this.reponseDonneesService = Objects.requireNonNull(givenAnswerService);
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
    public List<ProjectDto> listerLesProjets() {
        return projectRepository.findAll().stream().map(ProjectDto::new).toList();
    }

    /**
     * Endpoint to retrieve a project by id
     */
    @GetMapping("/projet/{id}")
    @ResponseBody
    public ResponseEntity<ProjectDto> recupererProjetAvecId(@PathVariable("id") Integer id) {
        var entity = projectRepository.findById(id);
        var dto = entity.map(ProjectDto::new).orElse(null);
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to retrieve a project by its user's token
     */
    @GetMapping("/projet/user")
    @ResponseBody
    public ResponseEntity<List<ProjectDto>> recupererProjetAvecToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        return ResponseEntity.ok(projectRepository.findByProfilMail(mail).stream().map(ProjectDto::new).toList());
    }



    /**
     * Endpoint to create a project
     *
     * @param authorizationHeader the token of the user
     * @param project              the project name
     * @return the project id
     */
    @PostMapping("/projet/create")
    public ResponseEntity<ProjectIdDto> createProject(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjectSimpleDto project) {
        if (project.getName().length() >= 50) {
            throw new IllegalArgumentException("Le nom du projet ne peut pas avoir un nom de plus de 50 caractères");
        }
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var profilEntityOptional = profilRepository.findByMail(mail);
        if (profilEntityOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        var profil = profilEntityOptional.get();
        if(profil.getIsAdmin() < 0){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        var projetEntity = ProjectEntity.builder()
                .nomProjet(project.getName())
                .profil(profil)
                .state(State.EN_COURS)
                .type(project.getType().equals("simulation") ? TypeP.SIMULATION : TypeP.PROJET)
                .build();
        projectRepository.save(projetEntity);
        return new ResponseEntity<>(new ProjectIdDto(projetEntity.getIdProjet()), HttpStatus.OK);
    }

    /**
     * Method to dissociate a project from a user and associate it to a default anonymous
     *
     * @param authorizationHeader the token of the user
     * @param projectDto           the project name
     * @return the project dto id
     */
    @PutMapping("/projet/delete")
    public ResponseEntity<ProjectIdDto> delete(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjectIdDto projectDto) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var profilEntityOptional = profilRepository.findByMail(mail);
        if (profilEntityOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        var project = projectRepository.findByIdProject(projectDto.getId());
        var mailOwner = project.getProfil().getMail();
        if (!mailOwner.equals(mail)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        var anoProfilOptional = profilRepository.findByMail("anonyme@demo.fr");
        if (anoProfilOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        project.setNomProjet(generateRandomString(8));
        project.setProfil(anoProfilOptional.get());
        projectRepository.save(project);
        return new ResponseEntity<>(new ProjectIdDto(project.getIdProjet()), HttpStatus.OK);

    }

    /**
     * Method to finish a project of a user
     *
     * @param authorizationHeader the token of the user
     * @param projectDto           the project name
     * @return the project dto id
     */
    @PutMapping("/projet/finish")
    public ResponseEntity<ProjectIdDto> finish(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjectIdDto projectDto) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var optionalProject = projetService.finish(mail, projectDto);
        if (optionalProject.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            var projet = optionalProject.get();
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
    public ResponseEntity<ProjectDto> copy(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjectIdDto projectIdDto) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var project = projectRepository.findByIdProject(projectIdDto.getId());
        var mailOwner = project.getProfil().getMail();
        var profile = profilRepository.findByMail(mail);

        // if token is not authorized in general or for this project
        if (profile.isEmpty() || !mailOwner.equals(mail)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        var projectEntity = ProjectEntity.builder()
                .nomProjet(projectIdDto.getProjectName())
                .profil(profile.get())
                .state(State.EN_COURS)
                .type(projectIdDto.getProjectType())
                .build();

        System.out.println("projectEntity = " + projectEntity);

        var projectCopy = projetService.save(projectEntity).orElseThrow();

        System.out.println("projectCopy = " + projectCopy);

        var answers = reponseDonneesService.findReponsesByProject(project);
        answers.forEach(answer -> answer.updateGivenAnswerProjectId(projectCopy));

        reponseDonneesService.saveResponseDonnees(answers);

        return new ResponseEntity<>(new ProjectDto(projectCopy), HttpStatus.OK);

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
