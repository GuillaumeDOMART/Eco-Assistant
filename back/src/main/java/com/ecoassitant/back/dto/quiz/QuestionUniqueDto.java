package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.tools.Categorie;
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
    private final ReponseDonneeDtoQuiz reponse;

    public Long getDependance() {
        return dependance;
    }




    /**
     * create a question with a quiz
     * @param quiz format tree
     */
    public QuestionUniqueDto(QuestionDto quiz) {
        this.intitule = quiz.getIntitule();
        this.phase = quiz.getPhase();
        this.questionId = quiz.getQuestionId();
        this.dependance = quiz.getDependance();
        this.type = quiz.getType();
        this.reponses = new ArrayList<>();
        quiz.getReponses().forEach(reponsePossibleDto -> {
            this.reponses.add(new ReponseUniqueDto(reponsePossibleDto));
        });
        this.reponse = quiz.getReponse();
    }

    /**
     * create quiz in the map
     * @param quiz quiz  format tree
     * @return quiz format map
     */
    public static Map<Phase, List<QuestionUniqueDto>> Mapper(QuestionDto quiz){
        HashMap<Phase,List<QuestionUniqueDto>> map = new HashMap<>();
        map.put(Phase.DEPLOIEMENT, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.DEVELOPPEMENT, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.MAINTENANCE, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.PLANIFICATION, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.TEST, new ArrayList<QuestionUniqueDto>());
        map.put(Phase.HORS_PHASE, new ArrayList<QuestionUniqueDto>());
        remplir(quiz, map);
        return map;
        }

    /**
     * filled the map with the quiz format tree
     * @param quiz quiz format tree
     * @param map map whose put for return it
     */
    private static void remplir(QuestionDto quiz, HashMap<Phase,List<QuestionUniqueDto>> map) {
        if (quiz == null)
            return;
        if (!map.get(quiz.getPhase()).stream().map(QuestionUniqueDto::getQuestionId).toList().contains(quiz.getQuestionId())) {
            var question = new QuestionUniqueDto(quiz);
            map.compute(question.getPhase(), (k, v) -> {
                v.add(question);
                return v;
            });
        }
        quiz.getReponses().forEach( reponsePossibleDto -> remplir(reponsePossibleDto.getQuestionSuiv(), map));
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
