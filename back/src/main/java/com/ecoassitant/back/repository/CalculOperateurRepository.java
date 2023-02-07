package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.CalculKey;
import com.ecoassitant.back.entity.CalculOperateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculOperateurRepository extends JpaRepository<CalculOperateurEntity, CalculKey> {
}
