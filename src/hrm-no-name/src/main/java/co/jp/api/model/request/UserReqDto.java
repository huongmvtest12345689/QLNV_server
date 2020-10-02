package co.jp.api.model.request;

import lombok.Data;

@Data
public class UserReqDto {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Integer roleId;
}
