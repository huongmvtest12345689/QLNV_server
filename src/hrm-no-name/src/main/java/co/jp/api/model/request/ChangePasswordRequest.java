package co.jp.api.model.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private int id;
    private String password;
    private String newPassword;
}
