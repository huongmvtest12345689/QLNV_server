package co.jp.api.model.request;

import lombok.Data;

@Data
public class UserResDto {
    private String name;
    private String email;
    private String password;
    private Integer rolesId;
    private Integer status;
    private Long displayOrder;
}
