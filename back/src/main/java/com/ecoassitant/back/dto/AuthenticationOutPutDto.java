
package com.ecoassitant.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for the output of authentication
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationOutPutDto {
    private String mail;
    private String token;
}