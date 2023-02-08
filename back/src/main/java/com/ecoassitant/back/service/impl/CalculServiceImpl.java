package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.calcul.CalculEntier;
import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.repository.CalculRepository;
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

    public CalculServiceImpl(CalculRepository calculRepository, ReponseDonneeRepository reponseDonneeRepository) {
        this.calculRepository = calculRepository;
        this.reponseDonneeRepository = reponseDonneeRepository;
    }

    @Override
    public List<Double> CalculsForProject(Long idProject) {
        var resultats = new ArrayList<Double>();
        var reponseDonnee = reponseDonneeRepository.findByReponseDonneeKey_Projet(idProject);
        var calculs = calculRepository.findAll();
        var map = new HashMap<Integer, List<CalculEntity>>();
        calculs.stream().map(calculEntity ->
            map.compute(calculEntity.getNbCalcul(), (k, v) -> {
                List<CalculEntity> result;
                if(v == null) {
                    result = List.of(calculEntity);
                } else {
                    v.add(calculEntity);
                    result = v;
                }
                return result;
            }));
        map.forEach((k, calcul)->{
            var calculEntier = new CalculEntier(calcul,reponseDonnee);
            var executer = calculEntier.execute();
            if(executer.isPresent())
                resultats.add(executer.get());
        });
        return resultats;
    }
}
