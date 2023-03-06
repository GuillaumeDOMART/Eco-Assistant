package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.resultat.GivenAnswerDto;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.GivenAnswerEntity;

import java.util.List;

/**
 * Service of ReponseDonnees
 */
public interface GivenAnswerService {
    /**
     * Service for save list of reponseDonnee
     *
     * @param responses payload with list of reponseDonnee and id project
     * @return true if save in bdd is successed
     */
    boolean saveResponseDonnees(GivenAnswerDto responses);

    /**
     * Method to find the responses associated with the given project
     *
     * @param projet project entity
     * @return a list of responseDonne from the projet
     */
    List<GivenAnswerEntity> findReponsesByProject(ProjectEntity projet);

    /**
     * Service for save list of already checked reponseDonneeEntity
     * If the reponses are not checked, uses saveResponseDonnees(GivenAnswerDto responses) instead
     *
     * @param responses list of reponseDonnee and id project
     * @return true if save in bdd has been successfull
     */
    void saveResponseDonnees(List<GivenAnswerEntity> responses);
}
