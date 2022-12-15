package fr.eco_assistant.dataBase;

public class Resultat {
    private final int projetId;
    private final int reponsePosId;
    private final int entry;

    public Resultat(int projetId, int reponsePosId, int entry){
        this.projetId = projetId;
        this.reponsePosId = reponsePosId;
        this.entry = entry;

    }

    public int getProjetId() {
        return projetId;
    }

    public int getReponsePosId() {
        return reponsePosId;
    }

    public int getEntry() {
        return entry;
    }
}
