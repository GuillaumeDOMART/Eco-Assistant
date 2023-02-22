package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.tools.Categorie;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;

import java.util.ArrayList;
import java.util.List;

/**
 * Question build with an QuestionEntity without id for use in app
 */
public class QuestionDto {
    private Long questionId;
    private String intitule;
    private TypeQ type;
    private Phase phase;
    private Categorie categorie;
    private  List<ReponsePossibleDto> reponses;
    private Long dependance;
    private ReponseDonneeDtoQuiz reponse;


    /**
     * Constructor of questionDto
     * @param question question Entity change into questionDto
     */
    public QuestionDto(QuestionEntity question) {
        if (question == null)
            return;
        this.questionId = question.getIdQuestion();
        this.intitule = question.getIntitule();
        this.reponse = null;
        this.type = question.getTypeQ();
        this.phase = question.getPhase();
        this.categorie = question.getCategorie();
        this.reponses = new ArrayList<>();
        question.getReponses().forEach(reponse -> reponses.add(new ReponsePossibleDto(reponse)));
        if (question.getDependance() == null)
            this.dependance = -1L;
        else
            this.dependance = question.getDependance().getIdQuestion();
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public TypeQ getType() {
        return type;
    }

    public void setType(TypeQ type) {
        this.type = type;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<ReponsePossibleDto> getReponses() {
        return reponses;
    }

    public void setReponses(List<ReponsePossibleDto> reponses) {
        this.reponses = List.copyOf(reponses);
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getDependance() {
        return dependance;
    }

    public void setDependance(Long dependance) {
        this.dependance = dependance;
    }

    /**
     * add all responses in the quiz
     * @param reponses list of response of th previous quiz
     */
    public void addReponses(List<ReponseDonneeEntity> reponses){
        reponses.forEach(reponseDonneeEntity -> addReponse(this, reponseDonneeEntity));
    }

    /**
     * add a response in the quiz
     * @param question a question
     * @param reponse response of th previous quiz
     */
    private void addReponse(QuestionDto question, ReponseDonneeEntity reponse){
        if (question == null)
            return;
        if (question.questionId == reponse.getReponseDonneeKey().getReponsePos().getQuestionAsso().getIdQuestion()){
            question.reponse = new ReponseDonneeDtoQuiz(reponse);
            return;
        }
        question.reponses.forEach(reponsePossibleDto -> addReponse(reponsePossibleDto.getQuestionSuiv(),reponse));
    }

    /**
     * get the response of the question complete previously else null
     * @return response of the previous quiz
     */
    public ReponseDonneeDtoQuiz getReponse() {
        return reponse;
    }
}
