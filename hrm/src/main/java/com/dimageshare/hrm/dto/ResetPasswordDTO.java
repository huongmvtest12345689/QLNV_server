package com.dimageshare.hrm.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordDTO {
    private String oldPassword;
    @NotNull(message = "password.is.empty")
    @Size(max = 45, min = 1, message = "password.over.length")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,16}", message = "invalid.password")
    private String newPassword;
}
