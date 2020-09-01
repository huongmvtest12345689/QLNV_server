package co.jp.api.service.impl;

import co.jp.api.dao.CountryDao;
import co.jp.api.dto.CountryResDto;
import co.jp.api.entity.Country;
import co.jp.api.service.CountryApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryApiServiceImpl implements CountryApiService {
    @Autowired
    private CountryDao countryDao;
    @Override
    public List<CountryResDto> getCountry() {
        List<Country> countryList = new ArrayList<>();
        List<CountryResDto> countryResDtoList = new ArrayList<>();
        countryList = this.countryDao.getCountryList();
        if (countryList.size() > 0) {
            for (Country country : countryList) {
                CountryResDto countryResDto = new CountryResDto();
                countryResDto.setId(country.getId());
                countryResDto.setCountryName(country.getCountryName());
                countryResDto.setCountryCode(country.getCountryCode());
                countryResDtoList.add(countryResDto);
            }
        } else {
            CountryResDto countryResDto = new CountryResDto();
            countryResDto.setId(1l);
            countryResDto.setCountryName("Việt Nam");
            countryResDto.setCountryCode("vi");
            countryResDtoList.add(countryResDto);
        }
        return countryResDtoList;
    }
}