package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.CalculEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * repository of Calcul
 */
@Repository
public interface CalculRepository extends JpaRepository<CalculEntity, Long> {
    /**
     * Find list of CalculEntity associated to a nbCalcul
     * @param nbCalcul represents the id of the group of calculation
     * @return List<CalculEntity>
     */
    List<CalculEntity> findByNbCalcul(int nbCalcul);

}
