package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.tools.Categorie;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionUniqueDto {
    private final Long questionId;
    private final String intitule;
    private final Phase phase;
    private final TypeQ type;
    private final Categorie categorie;
    private final List<ReponseUniqueDto> reponses;
    private static HashMap<Phase,List<QuestionUniqueDto>> map = new HashMap<>();

    public QuestionUniqueDto(QuestionDto quiz) {
        this.categorie = quiz.getCategorie();
        this.intitule = quiz.getIntitule();
        this.phase = quiz.getPhase();
        this.questionId = quiz.getQuestionId();
        this.type = quiz.getType();
        this.reponses = new ArrayList<>();
        quiz.getReponses().forEach(reponsePossibleDto -> {
            this.reponses.add(new ReponseUniqueDto(reponsePossibleDto));
        });
    }

    public static HashMap<Phase, List<QuestionUniqueDto>> Mapper(QuestionDto quiz){
        map.put(Phase.DEPLOIEMENT, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.DEVELOPPEMENT, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.MAINTENANCE, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.PLANIFICATION, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.TEST, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.HORS_PHASE, new ArrayList<QuestionUniqueDto>());
        remplir(quiz);
        return map;
        }

    private static void remplir(QuestionDto quiz) {
        if (quiz == null)
            return;
        if (!map.get(quiz.getPhase()).stream().map(QuestionUniqueDto::getQuestionId).toList().contains(quiz.getQuestionId())) {
            var question = new QuestionUniqueDto(quiz);
            map.compute(question.getPhase(), (k, v) -> {
                v.add(question);
                return v;
            });
        }
        quiz.getReponses().forEach( reponsePossibleDto -> remplir(reponsePossibleDto.getQuestionSuiv()));
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

    public Categorie getCategorie() {
        return categorie;
    }

    public List<ReponseUniqueDto> getReponses() {
        return reponses;
    }

    public TypeQ getType() {
        return type;
    }
}
