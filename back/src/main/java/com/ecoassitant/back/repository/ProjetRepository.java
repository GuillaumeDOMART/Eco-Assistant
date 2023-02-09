package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProjetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * repository of Project
 */
public interface ProjetRepository extends JpaRepository<ProjetEntity, Long> {
}
