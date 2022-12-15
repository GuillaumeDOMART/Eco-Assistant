package fr.eco_assistant.dataBase;

import fr.eco_assistant.dataBase.Enum.Categorie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Phase {
    private final HashMap<Categorie, List<QuestionR>> questions = new HashMap();
    public void addQuestion(Categorie categorie, QuestionR question){
        var listQuestion = questions.get(categorie);
        if (Objects.isNull(listQuestion)){
            listQuestion = new ArrayList<QuestionR>();
        }
        listQuestion.add(question);
        questions.put(categorie, listQuestion);
    }
    public List<QuestionR> getCategorie(Categorie categorie){
        return questions.get(categorie);
    }
}
