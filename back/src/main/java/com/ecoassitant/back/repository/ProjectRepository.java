package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.tools.TypeP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * repository of Project
 */
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    /**
     * Get ProjectEntity associated with the idProjet
     * @param idProject represents id of project
     * @return ProjectEntity
     */
    Optional<ProjectEntity> findByIdProject(Integer idProject);

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
