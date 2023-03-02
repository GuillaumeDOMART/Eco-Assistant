package com.ecoassitant.back.service;


import com.ecoassitant.back.dto.project.ProjectIdDto;
import com.ecoassitant.back.dto.project.ProjetDto;

import java.util.List;
import java.util.Optional;

/**
 * Service of ProjetService
 */
public interface ProjetService {
    /**
     * Get ProjetDto associated with the id
     * @param id represents id of project
     * @return ProjetDto
     */
    ProjetDto getProject(Integer id);

    /**
     * Get List<ProjetDto> associated with the Profil id
     * @param id represents id of profil
     * @return List<ProjetDto>
     */
    List<ProjetDto> findProjectByProfilId(Integer id);

    /**
     * Get all ProjetDTO in the DB
     * @return List<ProjetDto>
     */
    List<ProjetDto> findAll();

    /**
     * Endpoint to anonymize a profile
     * @param mail is the mail of the user connected
     * @return optional the id of the profile dissociated
     */
    Optional<ProjectIdDto> finish(String mail, ProjectIdDto projetDto);
}
