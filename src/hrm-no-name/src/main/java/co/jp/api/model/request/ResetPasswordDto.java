package co.jp.api.model.request;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String newPass;
    private String code;
    private String email;
}