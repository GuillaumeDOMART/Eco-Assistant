package com.ecoassitant.back.dto;

public class ProfilSimplDto {
    private String firstname;
    private String lastname;
    private String mail;
    private String mdp;

    public ProfilSimplDto(String firstname, String lastname, String mail, String mdp) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.mdp = mdp;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
