package co.jp.api.model.request;

import lombok.Data;

@Data
public class LoginReqDto {
    private String email;
    private String password;
}
