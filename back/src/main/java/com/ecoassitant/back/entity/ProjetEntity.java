package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Etat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Profil")
public class ProjetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProjet", nullable = false)
    private Long idProjet;

    @ManyToOne
    @JoinColumn(name = "profilId", nullable = false)
    private ProjetEntity profil;

    @Column(name = "nomProjet", nullable = false)
    private String nomProjet;

    @Column(name = "etat", nullable = false)
    @Enumerated(EnumType.STRING)
    private Etat etat;
}
