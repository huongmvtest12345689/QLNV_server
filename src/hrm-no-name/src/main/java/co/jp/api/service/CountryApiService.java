package co.jp.api.service;

import co.jp.api.dto.CountryResDto;

import java.util.List;

public interface CountryApiService {
    List<CountryResDto> getCountry();
}
