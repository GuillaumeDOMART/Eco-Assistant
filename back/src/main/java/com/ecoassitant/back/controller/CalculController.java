package com.ecoassitant.back.controller;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.*;
import com.ecoassitant.back.dto.resultat.ResultatsPhaseDto;
import com.ecoassitant.back.service.CalculService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * Controller for request about calcul
 */
@RequestMapping("api")
@RestController
public class CalculController {
    private final CalculService calculService;
    private final JwtService jwtService;

    /**
     * idProjet
     * Initalise the calculService
     *
     * @param calculService composite for using Service methode
     */
    @Autowired
    public CalculController(CalculService calculService, JwtService jwtService) {
        this.calculService = Objects.requireNonNull(calculService);
        this.jwtService = Objects.requireNonNull(jwtService);
    }

    /**
     * list of calcul for a resultat
     *
     * @param projectId id of the resultat
     * @return list of calculs executed
     */
    @PostMapping("/calculs")
    public ResponseEntity<ResultatsPhaseDto> resultatsCalcul(@RequestHeader("Authorization") String authorizationToken, @RequestBody IdDto projectId) {
        var token = authorizationToken.substring(7);
        var mail = jwtService.extractMail(token);
        var resultat = calculService.calculsForProject(projectId.getId(), mail);
        return resultat.map(resultatsPhaseDto -> new ResponseEntity<>(resultatsPhaseDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}



