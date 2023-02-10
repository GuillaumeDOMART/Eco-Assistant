package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProjetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of Project
 */
public interface ProjetRepository extends JpaRepository<ProjetEntity, Long> {
    /**
     * Get ProjetEntity associated with the idProjet
     * @param idProjet represents id of project
     * @return ProjetEntity
     */
    ProjetEntity findByIdProjet(Long idProjet);

    /**
     * Get List<ProjetEntity> associated with idProfil
     * @param idProfil represents id of project
     * @return List<ProjetEntity>
     */
    List<ProjetEntity> findByProfil_IdProfil(Long idProfil);

}
