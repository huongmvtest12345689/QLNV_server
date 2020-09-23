package co.jp.api.model.request;

import lombok.Data;

@Data
public class ResetByMailRequest {
    private String email;
    private String password;
}
