package com.ecoassitant.back.dto.project;

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

    public ProjectIdDto(int id) {
        this.id = id;
    }
}
