package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ProfilEntity;

import java.util.Objects;

public class ProfilDto {

    private Long id;

    private String mail;

    private String nom;

    private String prenom;

    private boolean isAdmin;

    public ProfilDto(ProfilEntity entity){
        Objects.requireNonNull(entity);

        this.id = entity.getIdProfil();
        this.mail = entity.getMail();
        this.nom = entity.getNom();
        this.prenom = entity.getPrenom();
        this.isAdmin = entity.isAdmin();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
