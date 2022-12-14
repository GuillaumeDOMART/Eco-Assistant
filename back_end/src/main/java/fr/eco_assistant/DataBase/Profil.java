package fr.eco_assistant.DataBase;

public record Profil(int id, String mail, String password, String nom, String prenom, boolean isAdmin) {
}
