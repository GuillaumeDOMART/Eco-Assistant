package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ConstanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of Constant
 */
public interface ConstantRepository extends JpaRepository<ConstanteEntity, Long> {
    /**
     * Get ConstanteEntity associated with id
     * @param idConstante
     * @return ConstanteEntity
     */
    ConstanteEntity findByIdConstante(Long idConstante);



}
