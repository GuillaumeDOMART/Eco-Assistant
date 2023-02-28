package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProjetEntity;
import com.ecoassitant.back.entity.tools.TypeP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of Project
 */
public interface ProjetRepository extends JpaRepository<ProjetEntity, Integer> {
    /**
     * Get ProjetEntity associated with the idProjet
     * @param idProjet represents id of project
     * @return ProjetEntity
     */
    ProjetEntity findByIdProjet(Integer idProjet);

    /**
     * Get List<ProjetEntity> associated with idProfil
     * @param idProfil represents id of project
     * @return List<ProjetEntity>
     */
    List<ProjetEntity> findByProfil_IdProfil(Integer idProfil);

    /**
     * Get List<ProjectEntity> associated with mail
     * @param mail the mail
     * @return List<ProjetEntity>
     */
    List<ProjetEntity> findByProfilMail(String mail);

    List<ProjetEntity> findByType(TypeP type);
}
