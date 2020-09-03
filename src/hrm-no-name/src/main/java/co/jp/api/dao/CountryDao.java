package co.jp.api.dao;

import co.jp.api.entity.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryDao {
    List<Country> getCountryList();
}