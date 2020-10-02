package co.jp.api.model.request;

import lombok.Data;

@Data
public class UserUpdateReqDto {
    private String email;
    private String name;
    private String phone;
    private Integer roleId;
}
