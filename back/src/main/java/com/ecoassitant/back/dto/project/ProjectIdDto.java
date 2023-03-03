package com.ecoassitant.back.dto.project;

import com.ecoassitant.back.entity.tools.TypeP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

/**
 * Dto for ProjectId
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectIdDto {
    private Integer id;

    @Nullable
    private String projectName = "noName";

    @Nullable
    private String projectType = "simulation";

    /**
     * getter of the project type
     *
     * @return the type of the project or the simulation type if null
     */
    @Nullable
    public TypeP getProjectType() {
        if (projectType == null) {
            return TypeP.SIMULATION;
        }
        try {
            return TypeP.valueOf(projectType);
        } catch (IllegalArgumentException e) {
            return TypeP.SIMULATION;
        }
    }

    /**
     * Constructor with only an id
     *
     * @param id id of the project
     */
    public ProjectIdDto(int id) {
        this.id = id;
    }
}
