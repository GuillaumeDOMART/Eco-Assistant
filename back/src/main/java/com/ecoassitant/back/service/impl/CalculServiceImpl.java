package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.calcul.CalculEntier;
import com.ecoassitant.back.dto.ResultatsPhaseDto;
import com.ecoassitant.back.dto.resultat.CalculDto;
import com.ecoassitant.back.dto.resultat.ResultatDto;
import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.repository.CalculRepository;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.service.CalculService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of calculService
 */
@Service
public class CalculServiceImpl implements CalculService {
    private final CalculRepository calculRepository;
    private final ReponseDonneeRepository reponseDonneeRepository;
    private final ProjetRepository projetRepository;

    private final ProfilRepository profilRepository;

    /**
     * Constructor of CalculService
     *
     * @param calculRepository        calculRepository composite for using Service methode
     * @param reponseDonneeRepository reponseDonneeRepository composite for using Service methode
     * @param projetRepository        projetRepository composite for using Service methode
     * @param profilRepository        profilRepository composite for using Service methods
     */
    public CalculServiceImpl(CalculRepository calculRepository, ReponseDonneeRepository reponseDonneeRepository, ProjetRepository projetRepository, ProfilRepository profilRepository) {
        this.calculRepository = calculRepository;
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.projetRepository = projetRepository;
        this.profilRepository = profilRepository;
    }

    /**
     * Function to get the result for a phase on a project
     *
     * @param idProject project of wich we want the result
     * @return the result
     */
    @Override
    public Optional<ResultatsPhaseDto> calculsForProject(Integer idProject, String mail) {
        var mine = resultatForProject(idProject);
        if (mine.isEmpty()) {
            return Optional.empty();
        }

        var resultat = new ResultatsPhaseDto(mine.get());
        var projects = projetRepository.findAll();
        if (projects.isEmpty()) {
            return Optional.empty();
        }
        projects.forEach(projetEntity -> resultat.addOther(resultatForProject(projetEntity.getIdProjet())));
        return Optional.of(resultat);
    }

    /**
     * Function to get the result for a project
     *
     * @param idProject the id of the project
     * @return the result
     */
    private Optional<ResultatDto> resultatForProject(Integer idProject) {
        var resultat = new ResultatDto();
        var projet = projetRepository.findById(idProject);
        if (projet.isEmpty())
            return Optional.empty();
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

        map.forEach((k, calcul) -> {
            var calculEntier = new CalculEntier(calcul, reponseDonnee);
            var executer = calculEntier.execute();
            var intitule = "test" + k;
            executer.ifPresent(aDouble -> {
                switch (calculEntier.getPhase()) {
                    case PLANIFICATION -> resultat.addPlanification(new CalculDto(intitule, aDouble));
                    case DEVELOPPEMENT -> resultat.addDeveloppement(new CalculDto(intitule, aDouble));
                    case DEPLOIEMENT -> resultat.addDeploiement(new CalculDto(intitule, aDouble));
                    case TEST -> resultat.addTest(new CalculDto(intitule, aDouble));
                    case MAINTENANCE -> resultat.addMaintenance(new CalculDto(intitule, aDouble));
                    default -> resultat.addHorsPhase(new CalculDto(intitule, aDouble));
                }
            });
        });
        return Optional.of(resultat);
    }
}
