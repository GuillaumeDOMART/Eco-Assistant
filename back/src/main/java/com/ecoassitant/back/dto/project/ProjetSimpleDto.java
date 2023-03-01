package com.ecoassitant.back.dto.project;

import com.ecoassitant.back.entity.tools.TypeP;
import lombok.Data;

/**
 * Dto for project with just name
 */
@Data
public class ProjetSimpleDto {
    private String nom;
    private String type;

}
