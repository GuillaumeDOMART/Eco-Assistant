package com.ecoassitant.back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "profil")
public class ProfilEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profil_sequence")
    @SequenceGenerator(name = "profil_sequence", sequenceName = "profil_sequence", allocationSize = 1)
    @Column(name = "idprofil", nullable = false)
    private Integer idProfil;

    @Column(name = "mail")
    @NotBlank(message = "Ne doit pas être vide")
    @Email(message = "L'adresse fournit n'est pas conforme")
    private String mail;

    @Column(name = "mdp", nullable = false)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?[#?!@$%^&*\\-]_).{8,}$", message = "Le mot de passe n'est pas conforme")
    private String password;

    @Column(name = "nom", nullable = false)
    @NotBlank(message = "Ne doit pas être vide")
    private String lastname;

    @Column(name = "prenom", nullable = false)
    @NotBlank(message = "Ne doit pas être vide")
    private String firstname;

    @Column(name = "isadmin", nullable = false)
    private Integer isAdmin;

}
