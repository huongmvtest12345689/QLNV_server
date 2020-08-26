package com.dimageshare.hrm.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    @NotNull(message = "username.is.empty")
    @Size(max = 45, min = 1, message = "username.over.length")
    private String account;
    @NotNull(message = "password.is.empty")
    @Size(max = 45, min = 1, message = "password.over.length")
    private String password;
}
