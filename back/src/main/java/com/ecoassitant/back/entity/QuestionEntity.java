package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Categorie;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "questionpre")
    private QuestionEntity questionPre;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeq", nullable = false)
    private TypeQ typeQ;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
    private Phase phase;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private Categorie categorie;

    @Column(name = "visibilite")
    @Type(type="boolean")
    private boolean visibilite;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionAsso")
    private List<ReponsePossibleEntity> reponses;

    /**
     * constructor of questionEntity
     * @param l id
     * @param q1 entitled
     * @param q previous question
     * @param type type of question
     * @param phase phase of question
     * @param categorie categorie of question
     * @param b visibility of question
     */
    public QuestionEntity(long l, String q1, QuestionEntity q, TypeQ type, Phase phase, Categorie categorie, boolean b) {
        this.idQuestion = l;
        this.intitule = q1;
        this.questionPre = q;
        this.typeQ = type;
        this.phase = phase;
        this.categorie = categorie;
        this.visibilite = b;
    }
}
