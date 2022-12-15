package fr.eco_assistant.DataBase;

public class Reponse {
    private final int id;
    private final int questionAsso;
    private final int questionSuiv;
    private final String intitule;

    public int getId() {
        return id;
    }

    public int getQuestionAsso() {
        return questionAsso;
    }

    public int getQuestionSuiv() {
        return questionSuiv;
    }

    public String getIntitule() {
        return intitule;
    }

    public int getConstanteId() {
        return constanteId;
    }

    private final int constanteId;

    public Reponse(int id, int questionAsso, int questionSuiv, String intitule, int constanteId){
        this.id = id;
        this.questionAsso = questionAsso;
        this.questionSuiv = questionSuiv;
        this.intitule = intitule;
        this.constanteId = constanteId;
    }
}
