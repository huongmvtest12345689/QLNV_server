package co.jp.api.model.response;

import lombok.Data;

@Data
public class JwtResDto {
    private final String jwtToken;

    public JwtResDto(String token) {
        this.jwtToken = token;
    }
}
