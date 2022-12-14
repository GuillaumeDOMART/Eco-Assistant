package fr.eco_assistant.DataBase;

import java.util.List;

public record QuestionR(Question question, List<Reponse> reponses) {
}
