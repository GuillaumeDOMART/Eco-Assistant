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
@Table(name = "Reponsepossible")
public class ReponsePossibleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreponsepos", nullable = false)
    private Long idReponsePos;

    @ManyToOne
    @JoinColumn(name = "questionasso", nullable = false)
    private QuestionEntity questionAsso;

    @ManyToOne
    @JoinColumn(name = "questionsuiv")
    private QuestionEntity questionSuiv;

    @Column(name = "intitule")
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "constanteid", nullable = false)
    private ConstanteEntity constante;
}
