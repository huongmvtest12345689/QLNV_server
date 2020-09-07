package co.jp.api.service;

import co.jp.api.model.response.CountryResDto;

import java.util.List;

public interface CountryApiService {
    List<CountryResDto> getCountry();
}
