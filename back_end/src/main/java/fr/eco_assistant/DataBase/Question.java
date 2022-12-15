package fr.eco_assistant.DataBase;

import fr.eco_assistant.DataBase.Enum.Categorie;
import fr.eco_assistant.DataBase.Enum.PhaseE;
import fr.eco_assistant.DataBase.Enum.TypeQ;

public class Question {
    private final int id;
    private final String intitule;
    private final int questionPre;
    private final TypeQ type;
    private final PhaseE phase;
    private final Categorie categorie;
    private final int calculQid;

    public int getId() {
        return id;
    }

    public String getIntitule() {
        return intitule;
    }

    public int getQuestionPre() {
        return questionPre;
    }

    public TypeQ getType() {
        return type;
    }

    public PhaseE getPhase() {
        return phase;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public int getCalculQid() {
        return calculQid;
    }

    public boolean isVisibilite() {
        return visibilite;
    }

    private final boolean visibilite;

    public Question(int id, String intitule, int questionPre, TypeQ type, PhaseE phase, Categorie categorie, int calculQid, boolean visibilite){
        this.id =id;
        this.intitule = intitule;
        this.questionPre = questionPre;
        this.type = type;
        this.phase = phase;
        this.categorie = categorie;
        this .calculQid = calculQid;
        this.visibilite = visibilite;
    }
}
