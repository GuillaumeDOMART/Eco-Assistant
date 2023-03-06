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
    private ProjectEntity projet;

    @OneToOne
    @JoinColumn(name = "questionid", nullable = false)
    private QuestionEntity question;

    public ProjectEntity getProjet() {
        return projet;
    }

    public void setProjet(ProjectEntity projet) {
        this.projet = projet;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
