package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.resultat.ReponseDonneesDto;
import com.ecoassitant.back.entity.ProjetEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;

import java.util.List;

/**
 * Service of ReponseDonnees
 */
public interface ReponseDonneesService {
    /**
     * Service for save list of reponseDonnee
     *
     * @param responses payload with list of reponseDonnee and id project
     * @return true if save in bdd is successed
     */
    boolean saveResponseDonnees(ReponseDonneesDto responses);

    /**
     * Method to find the responses associated with the given project
     *
     * @param projet project entity
     * @return a list of responseDonne from the projet
     */
    List<ReponseDonneeEntity> findReponsesByProject(ProjetEntity projet);

    /**
     * Service for save list of already checked reponseDonneeEntity
     * If the reponses are not checked, uses saveResponseDonnees(ReponseDonneesDto responses) instead
     *
     * @param responses list of reponseDonnee and id project
     * @return true if save in bdd has been successfull
     */
    void saveResponseDonnees(List<ReponseDonneeEntity> responses);

    ReponseDonneeEntity saveResponse(ReponseDonneeEntity answer);
}
