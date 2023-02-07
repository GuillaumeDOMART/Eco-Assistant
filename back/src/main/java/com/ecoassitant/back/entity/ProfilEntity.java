package com.ecoassitant.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Profil")
public class ProfilEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfil", nullable = false)
    private Long idProfil;

    @Column(name = "mail")
    private String mail;

    @Column(name = "mdp", nullable = false)
    private String password;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "isAdmin", nullable = false)
    @Type(type="boolean")
    private boolean isAdmin;
}
