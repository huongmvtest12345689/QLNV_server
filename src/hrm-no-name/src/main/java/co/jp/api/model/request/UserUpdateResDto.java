package co.jp.api.model.request;

import lombok.Data;

@Data
public class UserUpdateResDto {
    private String email;
    private String name;
    private String phone;
    private Integer roleId;
}
