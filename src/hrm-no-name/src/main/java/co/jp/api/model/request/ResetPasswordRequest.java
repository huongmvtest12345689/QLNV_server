package co.jp.api.model.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private  Integer id;
    private String code;
    private String newPassword;
}
