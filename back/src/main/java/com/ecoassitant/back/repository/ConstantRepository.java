package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ConstanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * repository of Constant
 */
public interface ConstantRepository extends JpaRepository<ConstanteEntity, Long> {
}
