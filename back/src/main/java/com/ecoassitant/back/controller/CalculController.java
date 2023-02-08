package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.IdDto;
import com.ecoassitant.back.service.CalculService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class CalculController {
    private final CalculService calculService;

    @Autowired
    public CalculController(CalculService calculService) {
        this.calculService = calculService;
    }
    @PostMapping("/calculs")
    public List<Double> resultatsCalcul(@RequestBody IdDto idProject){
        return calculService.CalculsForProject(idProject.getId());
    }
}



