package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.calcul.ReformedOperation;
import com.ecoassitant.back.dto.result.ResultatsPhaseDto;
import com.ecoassitant.back.dto.result.CalculDto;
import com.ecoassitant.back.dto.result.ResultDto;
import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeP;
import com.ecoassitant.back.repository.CalculRepository;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjectRepository;
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
    private final ProjectRepository projectRepository;

    private final ProfilRepository profilRepository;

    /**
     * Constructor of CalculService
     *
     * @param calculRepository        calculRepository composite for using Service methode
     * @param reponseDonneeRepository reponseDonneeRepository composite for using Service methode
     * @param projectRepository projetRepository composite for using Service methode
     */
    public CalculServiceImpl(CalculRepository calculRepository, ReponseDonneeRepository reponseDonneeRepository, ProjectRepository projectRepository, ProfilRepository profilRepository) {
        this.calculRepository = calculRepository;
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.projectRepository = projectRepository;
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
        var project = projectRepository.findById(idProject);
        if (project.isEmpty())
            return Optional.empty();

        var profil = profilRepository.findByMail(mail);
        if (profil.isEmpty())
            return Optional.empty();

        var currentIdProfil = project.get().getProfil().getIdProfil();
        var projectIdProfil = profil.get().getIdProfil();
        if (!currentIdProfil.equals(projectIdProfil) && profil.get().getIsAdmin() < 1)
            return Optional.empty();

        var mine = resultatForProject(idProject);
        if (mine.isEmpty()) {
            return Optional.empty();
        }

        var resultat = new ResultatsPhaseDto(mine.get());
        var projects = projectRepository.findByType(TypeP.PROJET);
        if (projects.isEmpty()) {
            return Optional.empty();
        }
        projects.forEach(projetEntity -> resultat.addOther(resultatForProject(projetEntity.getIdProjet())));
        return Optional.of(resultat);
    }

    /**
     * Create a map for sort calculs
     * @param calculs list of calculs entity
     * @return map
     */
    private Map<Integer, Map<Integer, List<CalculEntity>>> creationResultat(List<CalculEntity> calculs) {
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
    private Optional<ResultDto> resultatForProject(Integer idProject) {
        var resultat = new ResultDto();
        var projet = projectRepository.findById(idProject);
        if (projet.isEmpty())
            return Optional.empty();
        var reponseDonnee = reponseDonneeRepository.findByReponseDonneeKey_Projet(projet.get());
        if (reponseDonnee.isEmpty())
            return Optional.of(resultat);
        var calculs = calculRepository.findAll();
        var map = creationResultat(calculs);

        map.forEach((k, calculsPriorite) -> {
            Optional<Double> executer = Optional.empty();
            Optional<Phase> phase = Optional.empty();
            for (var calcul : calculsPriorite.values()) {
                var calculEntier = new ReformedOperation(calcul, reponseDonnee);
                executer = calculEntier.execute();
                if (executer.isPresent()) {
                    phase = Optional.ofNullable(calculEntier.getPhase());
                }
                executer.ifPresent(aDouble -> {
                    System.out.println(calculEntier.getEntitled() + " " + aDouble);
                    switch (calculEntier.getPhase()) {
                        case PLANIFICATION -> resultat.addPlanning(new CalculDto(calculEntier.getEntitled(), aDouble));
                        case DEVELOPPEMENT -> resultat.addDevelopment(new CalculDto(calculEntier.getEntitled(), aDouble));
                        case DEPLOIEMENT -> resultat.addDeployment(new CalculDto(calculEntier.getEntitled(), aDouble));
                        case TEST -> resultat.addTest(new CalculDto(calculEntier.getEntitled(), aDouble));
                        case MAINTENANCE -> resultat.addMaintenance(new CalculDto(calculEntier.getEntitled(), aDouble));
                        default -> resultat.addOutPhase(new CalculDto(calculEntier.getEntitled(), aDouble));
                    }
                });
            }
        });
            return Optional.of(resultat);
    }
    /**
     * Function to get the result for a calcul
     *
     * @param nbCalcul the id of the group of response of the same calcul
     * @return the result
     */
    public Map<Integer, Map<Integer, List<CalculEntity>>> resultatForCalcul(Integer nbCalcul) {
        var resultat = new ResultDto();
        var calculs = calculRepository.findByNbCalcul(nbCalcul);
        return  creationResultat(calculs);


    }
}
