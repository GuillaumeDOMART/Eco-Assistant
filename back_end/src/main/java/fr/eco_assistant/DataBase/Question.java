package fr.eco_assistant.DataBase;

import fr.eco_assistant.DataBase.Enum.Categorie;

public record Question(int id, String intitule, int questionPre, TypeQ type, PhaseE phase, Categorie categorie, int calculQid, boolean visibilite) {

}
