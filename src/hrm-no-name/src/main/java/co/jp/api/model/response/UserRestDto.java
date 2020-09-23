package co.jp.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserRestDto {
    private Integer id;
    private String token;
    private Date tokenStart;
    private Date tokenEnd;
}