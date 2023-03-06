package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.calcul.ReformedOperation;
import com.ecoassitant.back.dto.result.CalculDto;
import com.ecoassitant.back.dto.result.ResultDto;
import com.ecoassitant.back.dto.result.ResultsPhaseDto;
import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeP;
import com.ecoassitant.back.repository.CalculRepository;
import com.ecoassitant.back.repository.GivenAnswerRepository;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjectRepository;
import com.ecoassitant.back.service.CalculService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of calculService
 */
@Service
public class CalculServiceImpl implements CalculService {
    private final CalculRepository calculRepository;
    private final GivenAnswerRepository givenAnswerRepository;
    private final ProjectRepository projectRepository;

    private final ProfilRepository profilRepository;

    /**
     * Constructor of CalculService
     *
     * @param calculRepository        calculRepository composite for using Service methode
     * @param givenAnswerRepository responseDonneeRepository composite for using Service methode
     * @param projectRepository projectRepository composite for using Service methode
     */
    public CalculServiceImpl(CalculRepository calculRepository, GivenAnswerRepository givenAnswerRepository, ProjectRepository projectRepository, ProfilRepository profilRepository) {
        this.calculRepository = calculRepository;
        this.givenAnswerRepository = givenAnswerRepository;
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
    public Optional<ResultsPhaseDto> calculsForProject(Integer idProject, String mail) {
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

        var mine = resultForProject(idProject);
        if (mine.isEmpty()) {
            return Optional.empty();
        }

        var result = new ResultsPhaseDto(mine.get());
        var projects = projectRepository.findByType(TypeP.PROJECT);
        if (projects.isEmpty()) {
            return Optional.empty();
        }
        projects.forEach(projectEntity -> result.addOther(resultForProject(projectEntity.getIdProjet())));
        return Optional.of(result);
    }

    /**
     * Create a map for sort calculs
     * @param operations list of calculs entity
     * @return map
     */
    private Map<Integer, Map<Integer, List<CalculEntity>>> creationResult(List<CalculEntity> operations) {
        var map = new HashMap<Integer, Map<Integer, List<CalculEntity>>>();
        operations.forEach(calculEntity -> {
            if (!map.containsKey(calculEntity.getNbCalcul()))
                map.put(calculEntity.getNbCalcul(), new HashMap<>());
            var priorite = map.get(calculEntity.getNbCalcul());
            if (!priorite.containsKey(calculEntity.getPriority()))
                priorite.put(calculEntity.getPriority(), new ArrayList<>());
            var list = priorite.get(calculEntity.getPriority());
            list.add(calculEntity);
            priorite.put(calculEntity.getPriority(), list);
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
    private Optional<ResultDto> resultForProject(Integer idProject) {
        var result = new ResultDto();
        var project = projectRepository.findById(idProject);
        if (project.isEmpty())
            return Optional.empty();
        var givenAnswer = givenAnswerRepository.findByReponseDonneeKey_Projet(project.get());
        if (givenAnswer.isEmpty())
            return Optional.of(result);
        var calculs = calculRepository.findAll();
        var map = creationResult(calculs);

        map.forEach((k, calculsPriority) -> {
            Optional<Double> executed = Optional.empty();
            Optional<Phase> phase = Optional.empty();
            for (var calcul : calculsPriority.values()) {
                var reformedOperation = new ReformedOperation(calcul, givenAnswer);
                executed = reformedOperation.execute();
                if (executed.isPresent()) {
                    phase = Optional.ofNullable(reformedOperation.getPhase());
                }
                executed.ifPresent(aDouble -> {
                    switch (reformedOperation.getPhase()) {
                        case PLANNING -> result.addPlanning(new CalculDto(reformedOperation.getEntitled(), aDouble));
                        case DEVELOPMENT -> result.addDevelopment(new CalculDto(reformedOperation.getEntitled(), aDouble));
                        case DEPLOYMENT -> result.addDeployment(new CalculDto(reformedOperation.getEntitled(), aDouble));
                        case TEST -> result.addTest(new CalculDto(reformedOperation.getEntitled(), aDouble));
                        case MAINTENANCE -> result.addMaintenance(new CalculDto(reformedOperation.getEntitled(), aDouble));
                        default -> result.addOutPhase(new CalculDto(reformedOperation.getEntitled(), aDouble));
                    }
                });
            }
        });
            return Optional.of(result);
    }
    /**
     * Function to get the result for a calcul
     *
     * @param nbCalcul the id of the group of response of the same calcul
     * @return the result
     */
    public Map<Integer, Map<Integer, List<CalculEntity>>> resultForCalcul(Integer nbCalcul) {
        var resultDto = new ResultDto();
        var operations = calculRepository.findByNbCalcul(nbCalcul);
        return  creationResult(operations);


    }
}
