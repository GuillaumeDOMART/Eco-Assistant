package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProjetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetRepository extends JpaRepository<ProjetEntity, Long> {
    ProjetEntity findByIdProjet(Long idProjet);
    List<ProjetEntity> findByProfil_IdProfil(Long idProfil);

}
