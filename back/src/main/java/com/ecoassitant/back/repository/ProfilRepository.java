package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * repository of Profil
 */
public interface ProfilRepository extends JpaRepository<ProfilEntity, Long> {
}
