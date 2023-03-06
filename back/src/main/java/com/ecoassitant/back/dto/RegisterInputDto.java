package com.ecoassitant.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for the input of register
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInputDto {
    private String mail;
    private String password;
    private String lastName;
    private String firstName;
}
