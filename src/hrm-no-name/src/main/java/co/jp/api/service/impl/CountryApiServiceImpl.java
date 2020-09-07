package co.jp.api.service.impl;

import co.jp.api.dao.CountryDao;
import co.jp.api.model.response.CountryResDto;
import co.jp.api.entity.Country;
import co.jp.api.service.CountryApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
        }
        return countryResDtoList;
    }
}
