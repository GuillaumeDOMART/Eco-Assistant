package com.ecoassitant.back.controller;


import com.ecoassitant.back.dto.resultat.ReponseDonneesDto;
import com.ecoassitant.back.service.ReponseDonneesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * Save a list of reponseDonnee fo a project
     * @param reponseDonneesDto list of reponseDonnee for a project
     * @return true if save successed
     */
    @PostMapping("/reponsesDonnees")
    public ResponseEntity<Boolean> saveReponseDonnees(@RequestBody ReponseDonneesDto reponseDonneesDto){
        var isSave = reponseDonneesService.saveResponseDonnees(reponseDonneesDto);
        return isSave?new ResponseEntity<>(isSave, HttpStatus.OK):new ResponseEntity<>(isSave, HttpStatus.UNAUTHORIZED);
    }

}
