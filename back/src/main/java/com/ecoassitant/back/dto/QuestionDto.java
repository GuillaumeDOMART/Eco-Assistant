package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.tools.Categorie;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;

import java.util.ArrayList;
import java.util.List;

/**
 * Question build with an QuestionEntity without id for use in app
 */
public class QuestionDto {
    private String intitule;
    private TypeQ type;
    private Phase phase;
    private Categorie categorie;
    private  List<ReponsePossibleDto> reponses;
    private boolean isVisible;

    /**
     * Constructor of questionDto
     * @param question question Entity change into questionDto
     */
    public QuestionDto(QuestionEntity question) {
        if (question == null)
            return;
        this.intitule = question.getIntitule();
        this.isVisible = question.isVisibilite();
        this.type = question.getTypeQ();
        this.phase = question.getPhase();
        this.categorie = question.getCategorie();
        this.reponses = new ArrayList<ReponsePossibleDto>();
        question.getReponses().forEach(reponse -> {
            reponses.add(new ReponsePossibleDto(reponse));
        });

    }

    /**
     * getter of visible
     * @return  visivility of question in quiz
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * setter of visible
     * @param visible indicate if question visible in quiz
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
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
}
