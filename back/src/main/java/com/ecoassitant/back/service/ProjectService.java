package com.ecoassitant.back.service;


import com.ecoassitant.back.dto.project.ProjectDto;
import com.ecoassitant.back.dto.project.ProjectIdDto;
import com.ecoassitant.back.entity.ProjectEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service of ProjetService
 */
public interface ProjectService {
    /**
     * Get ProjectDto associated with the id
     * @param id represents id of project
     * @return ProjectDto
     */
    ProjectDto getProject(Integer id);

    /**
     * Get List<ProjectDto> associated with the Profil id
     * @param id represents id of profil
     * @return List<ProjectDto>
     */
    List<ProjectDto> findProjectByProfilId(Integer id);

    /**
     * Get all ProjetDTO in the DB
     * @return List<ProjectDto>
     */
    List<ProjectDto> findAll();

    /**
     * Endpoint to anonymize a profile
     * @param mail is the mail of the user connected
     * @return optional the id of the profile dissociated
     */
    Optional<ProjectIdDto> finish(String mail, ProjectIdDto projetDto);

    /**
     * Endpoint to save a projectEntity
     *
     * @param projet project that needs to be saved
     * @return the savec project
     */
    Optional<ProjectEntity> save(ProjectEntity projet);
}
