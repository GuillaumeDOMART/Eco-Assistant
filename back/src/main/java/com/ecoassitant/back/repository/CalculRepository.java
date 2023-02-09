package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.CalculEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository of Calcul
 */
@Repository
public interface CalculRepository extends JpaRepository<CalculEntity, Long> {

}
