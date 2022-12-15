package fr.eco_assistant.rest.profile;

import java.util.Objects;

public class Profile {
    public Profile(String nom, String prenom, String email, String password) {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        Objects.requireNonNull(nom);
        Objects.requireNonNull(prenom);
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    private final String nom;
    private final String prenom;
    private final String email;
    private final String password;

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
