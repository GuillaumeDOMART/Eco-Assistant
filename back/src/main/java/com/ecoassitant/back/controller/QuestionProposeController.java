package com.ecoassitant.back.controller;


import com.ecoassitant.back.dto.ConstanteDto;
import com.ecoassitant.back.entity.QuestionPropose;
import com.ecoassitant.back.service.ConstanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for request about Constance
 */

@RequestMapping("api")
@RestController
public class QuestionProposeController {

    private final QuestionProposeService constanteService;

    /**
     * Initialize the constanteService
     * @param questionProposeService composite for using Service methods
     */
    @Autowired
    public QuestionProposeController(QuestionProposeService questionProposeService) {
        this.questionProposeService = questionProposeService;
    }


    /**
     *get the constante associated with the id
     * @param constanteId represents id of constante
     * @return Constante associated with id

    @GetMapping("/constante/{id}")
    public ResponseEntity<ConstanteDto> getConstante(@PathVariable("id") Long constanteId){
        ConstanteDto constante = constanteService.getConstante(constanteId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(constante==null){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(constante,headers, HttpStatus.OK);
        }

    }

    /**
     * Get all constantes in the DB
     * @return all constantes

    @GetMapping("/constantes")
    public ResponseEntity<List<ConstanteDto>> getAllConstantes(){
        List<ConstanteDto> constantes = constanteService.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(constantes.isEmpty()){
            return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(constantes,headers,HttpStatus.OK);
        }
    }
    */
}
