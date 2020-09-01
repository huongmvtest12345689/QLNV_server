package co.jp.api.controller;

import co.jp.api.cmn.ResponseApi;
import co.jp.api.dto.CountryResDto;
import co.jp.api.service.CountryApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/country/")
public class Country {
    @Autowired
    private CountryApiService countryApiService;
    @GetMapping("/cmn/countryList")
    public List<CountryResDto> getListCountry(){
        List<CountryResDto> countryResDto = new ArrayList<>();
        countryResDto = this.countryApiService.getCountry();
        return countryResDto;
    }
}
