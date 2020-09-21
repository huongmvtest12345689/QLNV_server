package co.jp.api.model.response;

import lombok.Data;

@Data
public class UserRestDto {
    private Integer id;
    private String username;
    private String password;
}