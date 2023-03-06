package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.admin.BanDto;
import com.ecoassitant.back.dto.admin.BanProjectDto;
import com.ecoassitant.back.dto.project.ProjectDto;

import java.util.List;
import java.util.Optional;

/**
 * Service for admin endpoint
 */
public interface AdminService {

    /**
     * Function to ban a profile
     * @param banDto DTO that contain the ID of the profile
     * @return if the user is banned
     */
    boolean ban(BanDto banDto);

    /**
     * Function to ban a project
     * @param banDto DTO that contain the ID of the project
     * @return if the project is banned
     */
    Boolean banProject(BanProjectDto banDto);

    /**
     * Return projects finished from a user if the connected user is an admin
     * @param mail mail of user connected
     * @param profilId represents profile which we search the projects
     * @return finished projects (not simulations) from a profile
     */
    Optional<List<ProjectDto>> getProjectsFinishedFromUserId(String mail, Integer profilId);
}
