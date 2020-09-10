package co.jp.api.model.response;

import lombok.Data;

@Data
public class UserResDto {
	private Integer stt;
    private String name;
    private String email;
    private String password;
    private String roles;
    private String status;
    
}
