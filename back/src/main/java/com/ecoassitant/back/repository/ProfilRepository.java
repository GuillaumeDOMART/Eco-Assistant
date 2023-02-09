package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilRepository extends JpaRepository<ProfilEntity, Long> {

    /**
     * Retrieve a ProfilEntity by it's mail
     */
    ProfilEntity findByMail(String mail);

}
