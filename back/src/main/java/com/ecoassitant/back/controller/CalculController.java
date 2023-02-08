package com.ecoassitant.back.controller;

<<<<<<< HEAD

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/calcul")
@RestController
public class CalculController {
=======
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@RequestMapping("api/calcul")
@RestController
public class CalculController {

    private final EntityManager entityManager;

    @Autowired
    public CalculController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("run")
    public String test(){
        var list = entityManager.createQuery("SELECT rd FROM      rd JOIN ReponsePossibleEntity rp ON rd.reponseDonneeKey.reponsePos = rp JOIN ConstanteEntity c ON rp.constante = c JOIN CalculOperateurEntity co ON rp.WHERE rd.reponseDonneeKey.projet = :id", ReponseDonneeEntity.class)
                .getResultList();
        return "";
    }

>>>>>>> robin-create_test_calcul.sql
}
