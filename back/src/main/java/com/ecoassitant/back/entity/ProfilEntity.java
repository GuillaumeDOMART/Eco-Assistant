package com.ecoassitant.back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "profil")
public class ProfilEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprofil", nullable = false)
    private Long idProfil;

    @Column(name = "mail")
    private String mail;

    @Column(name = "mdp", nullable = false)
    private String password;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "isadmin", nullable = false)
    @Type(type="org.hibernate.type.NumericBooleanType")
    private boolean isAdmin;
}
