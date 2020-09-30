package co.jp.api.model.response;

import lombok.Data;

@Data
public class UserAllResDto {
    private Integer id;
    private String email;
    private String name;
    private String phone;
    private String roleName;
    private Integer roleId;
}
