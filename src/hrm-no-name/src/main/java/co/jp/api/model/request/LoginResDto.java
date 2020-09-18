package co.jp.api.model.request;

import lombok.Data;

@Data
public class LoginResDto {
    private String email;
    private String password;
}
