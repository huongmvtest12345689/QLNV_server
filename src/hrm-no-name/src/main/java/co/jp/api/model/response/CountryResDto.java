package co.jp.api.model.response;

import lombok.Data;

@Data
public class CountryResDto {
    private Long id;
    private String countryName;
    private String countryCode;
}