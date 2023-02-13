package com.ecoassitant.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInputDto {
    private String mail;
    private String password;
    private String nom;
    private String prenom;
}
