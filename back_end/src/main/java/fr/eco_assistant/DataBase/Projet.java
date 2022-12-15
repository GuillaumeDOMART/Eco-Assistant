package fr.eco_assistant.DataBase;

import fr.eco_assistant.DataBase.Enum.Etat;

import java.util.List;

public class Projet {
    private final Etat etat;
    private final int id;
    private final int profilId;
    private final String nomProjet;

    public Etat getEtat() {
        return etat;
    }

    public int getId() {
        return id;
    }

    public int getProfilId() {
        return profilId;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public List<Resultat> getResultats() {
        return resultats;
    }

    private final List<Resultat> resultats;

    public  Projet(int id, int profilId, String nomProjet, Etat etat, List<Resultat> resultats){
        this.id = id;
        this.profilId = profilId;
        this.nomProjet = nomProjet;
        this.etat = etat;
        this.resultats = resultats;
    }
}
