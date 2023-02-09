package com.ecoassitant.back.controller;


import com.ecoassitant.back.dto.ReponseDonneesDto;
import com.ecoassitant.back.service.ReponseDonneesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller for request about ReponseDonnee
 */
@RequestMapping("api")
@RestController
public class ReponseDonneeController {
    private final ReponseDonneesService reponseDonneesService;
    @Autowired
    public ReponseDonneeController(ReponseDonneesService reponseDonneesService) {
        this.reponseDonneesService = reponseDonneesService;
    }

    @PostMapping("/reponsesDonnees")
    public boolean saveReponseDonnees(@RequestBody ReponseDonneesDto reponseDonneesDto){
        return reponseDonneesService.saveResponseDonnees(reponseDonneesDto);
    }

}
