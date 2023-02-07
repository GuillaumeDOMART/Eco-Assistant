package com.ecoassitant.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ReponseDonnee")
public class ReponseDonneeEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "projetId", nullable = false)
    private ProjetEntity projet;

    @Id
    @ManyToOne
    @JoinColumn(name = "reponsePosId", nullable = false)
    private ReponsePossibleEntity reponsePos;

    @Column(name = "entry", nullable = false)
    private int entry;
}
