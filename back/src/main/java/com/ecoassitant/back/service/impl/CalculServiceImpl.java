package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.calcul.CalculEntier;
import com.ecoassitant.back.dto.ResultatsPhaseDto;
import com.ecoassitant.back.dto.resultat.CalculDto;
import com.ecoassitant.back.dto.resultat.ResultatDto;
import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.repository.CalculRepository;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.service.CalculService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of calculService
 */
@Service
public class CalculServiceImpl implements CalculService {
    private final CalculRepository calculRepository;
    private final ReponseDonneeRepository reponseDonneeRepository;
    private final ProjetRepository projetRepository;

    /**
     * Constructor of CalculService
     *
     * @param calculRepository        calculRepository composite for using Service methode
     * @param reponseDonneeRepository reponseDonneeRepository composite for using Service methode
     * @param projetRepository        projetRepository composite for using Service methode
     */
    public CalculServiceImpl(CalculRepository calculRepository, ReponseDonneeRepository reponseDonneeRepository, ProjetRepository projetRepository) {
        this.calculRepository = calculRepository;
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.projetRepository = projetRepository;
    }

    /**
     * Function to get the result for a phase on a project
     *
     * @param idProject project of wich we want the result
     * @return the result
     */
    @Override
    public ResultatsPhaseDto calculsForProject(Integer idProject) {
        var mine = resultatForProject(idProject);
        if (mine == null)
            return null;
        var resultat = new ResultatsPhaseDto(mine);

        System.out.println(resultat);
        var projects = projetRepository.findAll();
        if (projects.isEmpty())
            return null;
        projects.forEach(projetEntity -> resultat.addOther(resultatForProject(projetEntity.getIdProjet())));
        return resultat;
    }

    private Map<Integer, Map<Integer, List<CalculEntity>>> creationResultat(List<CalculEntity> calculs){
        var map = new HashMap<Integer, Map<Integer, List<CalculEntity>>>();
        calculs.forEach(calculEntity -> {
            if (!map.containsKey(calculEntity.getNbCalcul()))
                map.put(calculEntity.getNbCalcul(), new HashMap<>());
            var priorite = map.get(calculEntity.getNbCalcul());
            if (!priorite.containsKey(calculEntity.getPriorite()))
                priorite.put(calculEntity.getPriorite(), new ArrayList<>());
            var list = priorite.get(calculEntity.getPriorite());
            list.add(calculEntity);
            priorite.put(calculEntity.getPriorite(), list);
            map.put(calculEntity.getNbCalcul(), priorite);
        });
        return map;
    }

    /**
     * Function to get the result for a project
     *
     * @param idProject the id of the project
     * @return the result
     */
    private ResultatDto resultatForProject(Integer idProject) {
        var resultat = new ResultatDto();
        var projet = projetRepository.findById(idProject);
        if (projet.isEmpty())
            return null;
        var reponseDonnee = reponseDonneeRepository.findByReponseDonneeKey_Projet(projet.get());
        if (reponseDonnee.isEmpty())
            return resultat;
        var calculs = calculRepository.findAll();
        var map = creationResultat(calculs);
        System.out.println("hello");
        map.forEach((k, calculsPriorite) -> {
            Optional<Double> executer = Optional.empty();
            Optional<Phase> phase = Optional.empty();
            for (var calcul : calculsPriorite.values()) {
                var calculEntier = new CalculEntier(calcul, reponseDonnee);
                executer = calculEntier.execute();
                if (executer.isPresent()) {
                    phase = Optional.ofNullable(calculEntier.getPhase());
                    break;
                }
            }

            var intitule = "test" + k;
            if (executer.isPresent() && phase.isPresent()) {
                var aDouble = executer.get();
                switch (phase.get()) {
                    case PLANIFICATION -> resultat.addPlanification(new CalculDto(intitule, aDouble));
                    case DEVELOPPEMENT -> resultat.addDeveloppement(new CalculDto(intitule, aDouble));
                    case DEPLOIEMENT -> resultat.addDeploiement(new CalculDto(intitule, aDouble));
                    case TEST -> resultat.addTest(new CalculDto(intitule, aDouble));
                    case MAINTENANCE -> resultat.addMaintenance(new CalculDto(intitule, aDouble));
                    case HORS_PHASE -> resultat.addHorsPhase(new CalculDto(intitule, aDouble));
                }
            }
        });
        return resultat;
    }
}
