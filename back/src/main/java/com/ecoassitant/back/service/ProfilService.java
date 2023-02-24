package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ProfilDto;
import com.ecoassitant.back.dto.ProfilIdDto;
import com.ecoassitant.back.dto.ProfilSimplDto;

import java.util.Optional;

/**
 * Service for ProfilController
 */

public interface ProfilService {
    /**
     * Retrieve a profil by it id
     * @param id the profil id
     * @return a ProfilDto with the submitted ID, or null if it doesn't exist
     */
    ProfilDto getProfilByID(Integer id);

    /**
     * Retrieve a profil by it mail address
     * @param mail the profil mail
     * @return a ProfilDto with the submitted mail, or null if it doesn't exist
     */
    ProfilDto getProfilByMail(String mail);

    /**
     * Endpoint to create a user admin
     * @param profilDto profile to create
     * @return return the id of the profile
     */
    Integer createProfil(ProfilSimplDto profilDto);

    Optional<ProfilIdDto> deleteProfil(String mail);
}
