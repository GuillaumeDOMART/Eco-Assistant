package com.ecoassitant.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Class Entity of table Profil
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Profil")
public class CalculEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "calculOpId", nullable = false)
    private CalculOperateurEntity calculOp;

    @Id
    @OneToOne
    @JoinColumn(name = "reponsePossibleId", nullable = false)
    private ReponsePossibleEntity reponsePossible;

    @Column(name = "nbCalcul", nullable = false)
    private int nbCalcul;
}
