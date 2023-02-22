package com.ecoassitant.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for the input of authentication
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationInputDto {
    private String login;
    private String password;
}
