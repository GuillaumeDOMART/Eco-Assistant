package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.calcul.CalculEntier;
import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.repository.CalculRepository;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.service.CalculService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CalculServiceImpl  implements CalculService {
    private final CalculRepository calculRepository;
    private final ReponseDonneeRepository reponseDonneeRepository;
    private final ProjetRepository projetRepository;

    public CalculServiceImpl(CalculRepository calculRepository, ReponseDonneeRepository reponseDonneeRepository, ProjetRepository projetRepository) {
        this.calculRepository = calculRepository;
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public List<Double> CalculsForProject(Long idProject) {

        var resultats = new ArrayList<Double>();
        var projet = projetRepository.findById(idProject);
        if (!projet.isPresent())
            return null;
        var reponseDonnee = reponseDonneeRepository.findByReponseDonneeKey_Projet(projet.get());
        var calculs = calculRepository.findAll();

        var map = new HashMap<Integer, List<CalculEntity>>();
        calculs.forEach(calculEntity -> {
            if (!map.containsKey(calculEntity.getNbCalcul()))
                map.put(calculEntity.getNbCalcul(), new ArrayList<>());
            var list = map.get(calculEntity.getNbCalcul());
            list.add(calculEntity);
            map.put(calculEntity.getNbCalcul(), list);
        });

        map.forEach((k, calcul)->{

            var calculEntier = new CalculEntier(calcul,reponseDonnee);
            var executer = calculEntier.execute();
            System.out.println("executer = " + executer);
            if(executer.isPresent())
                resultats.add(executer.get());
        });
        return resultats;
    }
}
