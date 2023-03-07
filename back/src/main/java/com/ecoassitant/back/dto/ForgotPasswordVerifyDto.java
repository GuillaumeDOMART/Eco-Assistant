package com.ecoassitant.back.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * Dto that contain new password
 */
@Data
public class ForgotPasswordVerifyDto {
    @Nullable
    private String oldPassword = "";

    private String password;
}
