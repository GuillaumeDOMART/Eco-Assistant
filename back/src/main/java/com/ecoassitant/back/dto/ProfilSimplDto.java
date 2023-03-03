package com.ecoassitant.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto for profil
 */
@Data
@AllArgsConstructor
public class ProfilSimplDto {
    private String firstname;
    private String lastname;
    private String mail;
    private String mdp;

}
