package com.ecoassitant.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Dto for token
 */
@Data
@AllArgsConstructor
public class TokenDto {
    private String token;
}
