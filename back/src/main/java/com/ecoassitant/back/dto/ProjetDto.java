package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ProjetEntity;
import com.ecoassitant.back.entity.tools.Etat;

import java.util.Objects;

public class ProjetDto {
    private Long id;
    private ProfilDto profil;
    private String nomProjet;
    private Etat etat;

    /**
     * Generate a DTO from an entity
     *
     * @throws IllegalArgumentException if entity is null
     */
    public ProjetDto(ProjetEntity entity){
        Objects.requireNonNull(entity);

        this.id = entity.getIdProjet();
        this.profil = new ProfilDto(entity.getProfil());
        this.nomProjet = entity.getNomProjet();
        this.etat =entity.getEtat();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfilDto getProfil() {
        return profil;
    }

    public void setProfil(ProfilDto profil) {
        this.profil = profil;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }
}
