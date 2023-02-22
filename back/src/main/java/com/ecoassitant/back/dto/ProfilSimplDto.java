package com.ecoassitant.back.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto for profil
 */
@Data
@AllArgsConstructor
public class ProfilSimplDto {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Email(message = "L'adresse fournit n'est pas conforme")
    private String mail;
    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?[#?!@$%^&*\\-]).{8,}$")
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
