package fr.eco_assistant.dataBase;

public class Profil {
    private final int id;
    private final String mail;
    private final String password;
    private final String prenom;
    private final String nom;

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getPrenom() {
        return prenom;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    private final boolean isAdmin;

    public Profil(int id, String mail, String password, String nom, String prenom, boolean isAdmin){
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.prenom = prenom;
        this.nom = nom;
        this.isAdmin = isAdmin;
    }
}
