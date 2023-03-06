package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ConstantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository of Constant
 */
public interface ConstantRepository extends JpaRepository<ConstantEntity, Long> {
    /**
     * Get ConstantEntity associated with id
     * @param idConstante represents id of constant
     * @return ConstantEntity
     */
    ConstantEntity findByIdConstante(Long idConstante);



}
