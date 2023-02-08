package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculRepository extends JpaRepository<CalculEntity, Long> {

}
