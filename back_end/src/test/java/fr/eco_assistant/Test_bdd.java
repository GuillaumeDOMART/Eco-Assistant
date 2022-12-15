package fr.eco_assistant;

import fr.eco_assistant.dataBase.Database;
import fr.eco_assistant.dataBase.Profil;
import fr.eco_assistant.dataBase.Service.ProfilService;

public class Test_bdd {
    public static void main(String[] args) {
        var database = new Database();
        database.createDatabase();
        var profilService = new ProfilService();
        var profilForax = new Profil(0, "javaforever@gmail.com", "pythoncestnul", "forax", "remi", false);
        profilService.createProfil(profilForax);

        var profilRecuperer = profilService.getProfil(0);
        System.out.println(profilRecuperer);

        database.deleteDatabase();
    }
}
