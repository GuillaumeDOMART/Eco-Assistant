package com.ecoassitant.back.dto.project;

import com.ecoassitant.back.dto.profil.ProfilDto;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.tools.Etat;
import com.ecoassitant.back.entity.tools.TypeP;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * DTO of a ProjectEntity
 */
@Data
@AllArgsConstructor
public class ProjetDto {
    private Integer id;
    private ProfilDto profil;
    private String nomProjet;
    private Etat etat;
    private TypeP type;

    /**
     * Generate a DTO from an entity
     *
     * @throws IllegalArgumentException if entity is null
     */
    public ProjetDto(ProjectEntity entity) {
        Objects.requireNonNull(entity);
        this.id = entity.getIdProjet();
        this.profil = new ProfilDto(entity.getProfil());
        this.nomProjet = entity.getNomProjet();
        this.etat = entity.getEtat();
        this.type = entity.getType();
    }

}
