package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Etat;
import com.ecoassitant.back.entity.tools.TypeP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "projet")
public class ProjetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_sequence")
    @SequenceGenerator(name = "project_sequence", sequenceName = "project_sequence", allocationSize = 1)
    @Column(name = "idprojet", nullable = false)
    private Integer idProjet;

    @ManyToOne
    @JoinColumn(name = "profilid", nullable = false)
    private ProfilEntity profil;

    @Column(name = "nomprojet", nullable = false)
    private String nomProjet;

    @Column(name = "etat", nullable = false)
    @Enumerated(EnumType.STRING)
    private Etat etat;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeP type;


}
