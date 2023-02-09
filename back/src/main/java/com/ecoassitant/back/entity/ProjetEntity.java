package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Etat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projet")
public class ProjetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprojet", nullable = false)
    private Long idProjet;

    @ManyToOne
    @JoinColumn(name = "profilid", nullable = false)
    private ProfilEntity profil;

    @Column(name = "nomprojet", nullable = false)
    private String nomProjet;

    @Column(name = "etat", nullable = false)
    @Enumerated(EnumType.STRING)
    private Etat etat;
}
