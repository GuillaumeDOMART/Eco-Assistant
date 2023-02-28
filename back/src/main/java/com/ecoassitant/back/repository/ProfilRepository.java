package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * repository of Profil
 */
public interface ProfilRepository extends JpaRepository<ProfilEntity, Integer> {


    /**
     * Retrieve a ProfilEntity by it's mail
     */
    Optional<ProfilEntity> findByMail(String mail);

    List<ProfilEntity> findByIsAdmin(Integer isAdmin);


}
