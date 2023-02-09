package com.ecoassitant.back.controller;


import com.ecoassitant.back.dto.ReponseDonneeDto;
import com.ecoassitant.back.repository.ConstantRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class ReponseDonneeController {

    private final ReponseDonneeRepository reponseDonneeRepo;

    @Autowired
    public ReponseDonneeController(ReponseDonneeRepository reponseDonneeRepo) {
        this.reponseDonneeRepo = reponseDonneeRepo;
    }

    @GetMapping("/reponsed/{id}")
    public ResponseEntity<List<ReponseDonneeDto>> getReponsesDonneesById(@RequestParam("id") long id){
        var reponses = reponseDonneeRepo.findById(id).stream().map(ReponseDonneeDto::new).toList();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(reponses.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(reponses,headers, HttpStatus.OK);
        }
    }
}
