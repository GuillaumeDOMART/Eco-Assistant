package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ProfilEntity;

import java.util.Objects;

public class ProfilDto {

    private Integer id;

    private String mail;

    private String nom;

    private String prenom;

    private Integer isAdmin;

    public ProfilDto(ProfilEntity entity){
        Objects.requireNonNull(entity);

        this.id = entity.getIdProfil();
        this.mail = entity.getMail();
        this.nom = entity.getNom();
        this.prenom = entity.getPrenom();
        this.isAdmin = entity.getIsAdmin();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Integer admin) {
        isAdmin = admin;
    }
}
