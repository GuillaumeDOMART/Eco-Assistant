package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.calcul.CalculEntier;
import com.ecoassitant.back.dto.CalculDto;
import com.ecoassitant.back.dto.ResultatDto;
import com.ecoassitant.back.dto.ResultatsPhaseDto;
import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.repository.CalculRepository;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.service.CalculService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of calculService
 */
@Service
public class CalculServiceImpl  implements CalculService {
    private final CalculRepository calculRepository;
    private final ReponseDonneeRepository reponseDonneeRepository;
    private final ProjetRepository projetRepository;

    /**
     * Constructor of CalculService
     * @param calculRepository calculRepository composite for using Service methode
     * @param reponseDonneeRepository reponseDonneeRepository composite for using Service methode
     * @param projetRepository projetRepository composite for using Service methode
     */
    public CalculServiceImpl(CalculRepository calculRepository, ReponseDonneeRepository reponseDonneeRepository, ProjetRepository projetRepository) {
        this.calculRepository = calculRepository;
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public ResultatsPhaseDto calculsForProject(Integer idProject) {
        var mine = resultatForProject(idProject);
        if (mine == null)
            return null;
        var resultat = new ResultatsPhaseDto(mine);

        var projects = projetRepository.findAll();
        if (projects.isEmpty())
            return null;
        projects.forEach(projetEntity -> resultat.addOther(resultatForProject(projetEntity.getIdProjet())));
        return resultat;
    }

    private ResultatDto resultatForProject(Integer idProject){
        var resultat = new ResultatDto();
        var projet = projetRepository.findById(idProject);
        if (projet.isEmpty())
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
            var intitule = "test" + k;
            executer.ifPresent(aDouble -> {
                switch (calculEntier.getPhase()){
                    case PLANIFICATION -> resultat.addPlanification(new CalculDto(intitule, aDouble));
                    case DEVELOPPEMENT -> resultat.addDeveloppement(new CalculDto(intitule, aDouble));
                    case DEPLOIEMENT -> resultat.addDeploiement(new CalculDto(intitule, aDouble));
                    case TEST -> resultat.addTest(new CalculDto(intitule, aDouble));
                    case MAINTENANCE -> resultat.addMaintenance(new CalculDto(intitule, aDouble));
                    default -> resultat.addHorsPhase(new CalculDto(intitule, aDouble));
                }
            });
        });
        return resultat;
    }
}
