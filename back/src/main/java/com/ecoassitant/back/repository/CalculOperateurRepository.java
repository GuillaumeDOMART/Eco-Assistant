package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.CalculOperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository of CalculOperateur
 */
public interface CalculOperateurRepository extends JpaRepository<CalculOperatorEntity, Long> {
}
