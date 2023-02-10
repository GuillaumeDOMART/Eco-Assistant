package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.IdDto;
import com.ecoassitant.back.dto.ResultatDto;
import com.ecoassitant.back.service.CalculService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        this.calculService = calculService;
    }

    /**
     * list of calcul for a resultat
     * @param projectId id of the resultat
     * @return list of calculs executed
     */
    @PostMapping("/calculs")
    public ResultatDto resultatsCalcul(@RequestBody IdDto projectId){
        return calculService.CalculsForProject(projectId.getId());
    }
}



