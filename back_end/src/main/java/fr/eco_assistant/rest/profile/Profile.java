package fr.eco_assistant.rest.profile;

import org.springframework.lang.NonNullApi;

import java.util.Objects;

public class Profile {
    public Profile(String email, String password) {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        this.email = email;
        this.password = password;
    }

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
