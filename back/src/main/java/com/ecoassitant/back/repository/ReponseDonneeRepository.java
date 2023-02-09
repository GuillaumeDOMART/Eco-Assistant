package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ReponseDonneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReponseDonneeRepository extends JpaRepository<ReponseDonneeEntity, Long> {
    List<ReponseDonneeEntity> findByReponseDonneeKey_Projet_IdProjet(Long idProjet);

}
