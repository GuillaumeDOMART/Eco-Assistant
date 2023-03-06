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
public class GivenAnswerKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "projetid", nullable = false)
    private ProjectEntity project;

    @OneToOne
    @JoinColumn(name = "questionid", nullable = false)
    private QuestionEntity question;

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
