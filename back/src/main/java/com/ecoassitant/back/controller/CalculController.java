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

    /**idProjet
     * Initalise the calculService
     * @param calculService composite for using Service methode
     */
    @Autowired
    public CalculController(CalculService calculService) {
        this.calculService = Objects.requireNonNull(calculService);
    }

    /**
     * list of calcul for a resultat
     * @param projectId id of the resultat
     * @return list of calculs executed
     */
    @PostMapping("/calculs")
    public ResponseEntity<ResultatsPhaseDto> resultatsCalcul(@RequestBody IdDto projectId){
        var resultat = calculService.calculsForProject(projectId.getId());
        return resultat != null? new ResponseEntity<>(resultat, HttpStatus.OK): new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}



