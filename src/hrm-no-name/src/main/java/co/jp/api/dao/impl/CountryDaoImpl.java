package co.jp.api.dao.impl;

import co.jp.api.entity.Country;
import co.jp.api.exception.UserHandleException;
import co.jp.api.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import co.jp.api.dao.CountryDao;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryDaoImpl implements CountryDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Country> getCountryList() {
        List<Country> countryList = new ArrayList<>();
        String sql = AppUtils.sqlExcute("cmn/COUNTRY_01_SELECT_ALL.sql");
        countryList = this.entityManager.createNativeQuery(sql, Country.class).getResultList();
        return countryList;
    }
}