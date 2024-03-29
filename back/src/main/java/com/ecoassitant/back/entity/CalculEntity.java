package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Phase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class Entity of table Profil
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calcul")
public class CalculEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcalcul", nullable = false)
    private Long idCalcul;

    @ManyToOne
    @JoinColumn(name = "calculopid", nullable = false)
    private CalculOperateurEntity calculOp;

    @OneToOne
    @JoinColumn(name = "reponsepossibleid", nullable = false)
    private ReponsePossibleEntity reponsePossible;

    @Column(name = "nbcalcul", nullable = false)
    private int nbCalcul;
    @Column(name = "priorite", nullable = false)
    private int priorite;
    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
    private Phase phase;
}

