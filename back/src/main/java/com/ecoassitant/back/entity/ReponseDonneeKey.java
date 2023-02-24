package com.ecoassitant.back.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * primary key of ReponseDonnee
 */
@Embeddable
public class ReponseDonneeKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "projetid", nullable = false)
    private ProjetEntity projet;

    @OneToOne
    @JoinColumn(name = "questionid", nullable = false)
    private QuestionEntity question;

    public ProjetEntity getProjet() {
        return projet;
    }

    public void setProjet(ProjetEntity projet) {
        this.projet = projet;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
