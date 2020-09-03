package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.cmn.ResponseApi;
import co.jp.api.dto.CountryResDto;
import co.jp.api.exception.UserHandleException;
import co.jp.api.service.CountryApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/country/")
public class CountryApiController {
    /*private static final Logger logger = LoggerFactory.getLogger(CountryApiController.class);*/
    @Autowired
    private CountryApiService countryApiService;

    @GetMapping("cmn/countryList")
    @ExceptionHandler(UserHandleException.class)
    public ResourceResponse getListCountry(){
//        logger.info("/cmn/countryList");
        List<CountryResDto> countryResDto = new ArrayList<>();
        countryResDto = this.countryApiService.getCountry();
        for (int i =0 ; i< 10; i++) {
            String a = countryResDto.get(i).getCountryName();
        }
        return new ResourceResponse(countryResDto);
    }
}
