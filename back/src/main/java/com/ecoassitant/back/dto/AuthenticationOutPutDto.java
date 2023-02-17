package com.ecoassitant.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationOutPutDto {
    private Integer id;
    private String mail;
    private String token;
}