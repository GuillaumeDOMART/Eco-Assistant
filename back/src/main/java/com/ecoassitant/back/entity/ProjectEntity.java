package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.State;
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
public class ProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_sequence")
    @SequenceGenerator(name = "project_sequence", sequenceName = "project_sequence", allocationSize = 1)
    @Column(name = "idprojet", nullable = false)
    private Integer idProject;

    @ManyToOne
    @JoinColumn(name = "profilid", nullable = false)
    private ProfilEntity profil;

    @Column(name = "nomprojet", nullable = false)
    private String projectName;

    @Column(name = "etat", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeP type;


}
