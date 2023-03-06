package com.ecoassitant.back.dto.project;

import com.ecoassitant.back.dto.profil.ProfilDto;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.tools.State;
import com.ecoassitant.back.entity.tools.TypeP;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * DTO of a ProjectEntity
 */
@Data
@AllArgsConstructor
public class ProjectDto {
    private Integer id;
    private ProfilDto profile;
    private String projectName;
    private State state;
    private TypeP type;

    /**
     * Generate a DTO from an entity
     *
     * @throws IllegalArgumentException if entity is null
     */
    public ProjectDto(ProjectEntity entity) {
        Objects.requireNonNull(entity);
        this.id = entity.getIdProject();
        this.profile = new ProfilDto(entity.getProfil());
        this.projectName = entity.getProjectName();
        this.state = entity.getState();
        this.type = entity.getType();
    }

}
