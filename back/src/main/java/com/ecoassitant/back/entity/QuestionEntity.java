package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class QuestionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idquestion", nullable = false)
    private Long idQuestion;

    @Column(name = "intitule", nullable = false)
    private String entitled;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeq", nullable = false)
    private TypeQ typeQ;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
    private Phase phase;


    @ManyToOne
    @JoinColumn(name = "dependance")
    private ResponsePossibleEntity dependency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionAsso")
    private List<ResponsePossibleEntity> responses;

    /**
     * constructor of questionEntity
     * @param l id
     * @param q1 entitled
     * @param type type of question
     * @param phase phase of question
     * @param dep dependance of question
     */
    public QuestionEntity(long l, String q1, TypeQ type, Phase phase, ResponsePossibleEntity dep) {
        this.idQuestion = l;
        this.entitled = q1;
        this.typeQ = type;
        this.phase = phase;
        this.dependency = dep;
    }
}
