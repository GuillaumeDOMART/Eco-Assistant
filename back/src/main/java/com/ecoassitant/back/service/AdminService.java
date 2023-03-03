package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.admin.BanDto;
import com.ecoassitant.back.dto.admin.BanProjectDto;
import com.ecoassitant.back.dto.project.ProjetDto;

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

    Optional<List<ProjetDto>> getProjectsFinishedFromUserId(String mail, Integer profilId);
}
