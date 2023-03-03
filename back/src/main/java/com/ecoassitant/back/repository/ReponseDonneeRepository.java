package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProjetEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * repository of ReponseDonnee
 */
public interface ReponseDonneeRepository extends JpaRepository<ReponseDonneeEntity, Long> {

    /**
     * give a list of ReponseDonne for a project
     * @param projet project linked of the ReponseDonnee
     * @return list ReponseDonnee linked of project
     */
    List<ReponseDonneeEntity> findByReponseDonneeKey_Projet(ProjetEntity projet);
}
