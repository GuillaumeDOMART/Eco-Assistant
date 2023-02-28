package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question with Id for questionSuiv
 */
public class QuestionUniqueDto {
    private final Long questionId;
    private final String intitule;
    private final Phase phase;
    private final TypeQ type;
    private final Long dependance;
    private final List<ReponseUniqueDto> reponses;
    private ReponseDonneeDtoQuiz reponse;

    public Long getDependance() {
        return dependance;
    }




    /**
     * create a question with a quiz
     * @param quiz format Entity
     */
    public QuestionUniqueDto(QuestionEntity quiz) {
        this.intitule = quiz.getIntitule();
        this.phase = quiz.getPhase();
        this.questionId = quiz.getIdQuestion();
        this.dependance = quiz.getDependance().getIdReponsePos();
        this.type = quiz.getTypeQ();
        this.reponses = new ArrayList<>();
        quiz.getReponses().forEach(reponsePossibleEntity -> reponses.add(new ReponseUniqueDto(reponsePossibleEntity)));
        this.reponse = null;
    }

    /**
     * set the reponse of the previous quiz
     * @param reponse reponse of the previous quiz
     */
    public void remplir(ReponseDonneeEntity reponse) {
        if (questionId == reponse.getReponseDonneeKey().getQuestion().getIdQuestion())
            this.reponse = new ReponseDonneeDtoQuiz(reponse);
    }

    public ReponseDonneeDtoQuiz getReponse() {
        return reponse;
    }
    public Long getQuestionId() {
        return questionId;
    }

    public String getIntitule() {
        return intitule;
    }

    public Phase getPhase() {
        return phase;
    }

    public List<ReponseUniqueDto> getReponses() {
        return reponses;
    }

    public TypeQ getType() {
        return type;
    }

}
