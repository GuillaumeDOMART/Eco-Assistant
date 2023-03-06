package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.tools.TypeP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of Project
 */
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    /**
     * Get ProjetEntity associated with the idProjet
     * @param idProjet represents id of project
     * @return ProjetEntity
     */
    ProjectEntity findByIdProjet(Integer idProjet);

    /**
     * Get List<ProjetEntity> associated with idProfil
     * @param idProfil represents id of project
     * @return List<ProjetEntity>
     */
    List<ProjectEntity> findByProfil_IdProfil(Integer idProfil);

    /**
     * Get List<ProjectEntity> associated with mail
     * @param mail the mail
     * @return List<ProjetEntity>
     */
    List<ProjectEntity> findByProfilMail(String mail);

    /**
     * Get list of projects by type
     * @param type type wanted
     * @return list of projet same type
     */
    List<ProjectEntity> findByType(TypeP type);
}
