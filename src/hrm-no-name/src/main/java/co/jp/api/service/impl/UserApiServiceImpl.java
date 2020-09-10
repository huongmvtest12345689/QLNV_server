package co.jp.api.service.impl;

import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.model.request.UserResDto;
import co.jp.api.model.response.CountryResDto;
import co.jp.api.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserApiServiceImpl implements UserApiService {
    @Autowired
    private UserDao userDao;
    public Boolean importFileExcel(List<User> listUsers){
        List<UserResDto> userResDtoList = new ArrayList<>();
        for (User user : listUsers) {
            UserResDto userResDto = new UserResDto();
            userResDto.setName(user.getName());
            userResDto.setEmail(user.getEmail());
            userResDto.setPassword(user.getPassword());
            userResDto.setRolesId(user.getRolesId());
            userResDto.setStatus(user.getStatus());
            userResDto.setDisplayOrder((long) 0);
            userResDtoList.add(userResDto);
        }
        return userDao.importUserFromFile(userResDtoList);
    }
}
