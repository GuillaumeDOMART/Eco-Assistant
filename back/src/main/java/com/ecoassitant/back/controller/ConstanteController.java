package com.ecoassitant.back.controller;


import com.ecoassitant.back.dto.ConstanteDto;
import com.ecoassitant.back.entity.ConstanteEntity;
import com.ecoassitant.back.repository.ConstantRepository;
import com.ecoassitant.back.service.ConstanteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller for request about Constance
 */

@RequestMapping("api")
@RestController
public class ConstanteController {

    private final ConstanteService constanteService;

    @Autowired
    public ConstanteController(ConstanteService constanteService) {
        this.constanteService = constanteService;
    }


    @GetMapping("/constante/{id}")
    public ResponseEntity<ConstanteDto> getConstante(@RequestParam("id") Long constanteId){
        constanteService.getConstante(constanteId);
        ConstanteDto constante = constanteService.getConstante(constanteId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(constante==null){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(constante,headers, HttpStatus.OK);
        }

    }

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

}
