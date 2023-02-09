package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ConstanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstantRepository extends JpaRepository<ConstanteEntity, Long> {
    ConstanteEntity findByIdConstante(Long idConstante);



}
