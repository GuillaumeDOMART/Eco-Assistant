package com.ecoassitant.back.controller;


import com.ecoassitant.back.dto.result.GivenAnswersDto;
import com.ecoassitant.back.service.GivenAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Controller for request about ReponseDonnee
 */
@RequestMapping("api")
@RestController
public class GivenAnswerController {
    private final GivenAnswerService givenAnswerService;

    /**
     * Constructor GivenAnswerController
     * @param givenAnswerService ReponseDonneesService
     */
    @Autowired
    public GivenAnswerController(GivenAnswerService givenAnswerService) {
        Objects.requireNonNull(givenAnswerService);
        this.givenAnswerService = givenAnswerService;
    }

    /**
     * Save a list of reponseDonnee fo a project
     *
     * @param givenAnswersDto list of reponseDonnee for a project
     * @return true if save successed, otherwise false
     */
    @PostMapping("/reponsesDonnees")
    public ResponseEntity<Boolean> saveGivenAnswer(@RequestBody GivenAnswersDto givenAnswersDto) {
        var isSave = givenAnswerService.saveResponseDonnees(givenAnswersDto);
        return isSave ? new ResponseEntity<>(isSave, HttpStatus.OK) : new ResponseEntity<>(isSave, HttpStatus.UNAUTHORIZED);
    }

}
