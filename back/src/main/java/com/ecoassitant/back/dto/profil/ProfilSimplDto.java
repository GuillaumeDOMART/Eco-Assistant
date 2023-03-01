package com.ecoassitant.back.dto.profil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto for profil
 */
@Data
@AllArgsConstructor
public class ProfilSimplDto {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Email(message = "L'adresse fournit n'est pas conforme")
    private String mail;
    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?[#?!@$%^&*\\-]).{8,}$")
    private String mdp;

}
