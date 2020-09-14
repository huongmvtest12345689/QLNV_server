package co.jp.api.service.impl;

import co.jp.api.dao.impl.UserDao;
import co.jp.api.entity.User;
import co.jp.api.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {
    @Autowired
    UserDao userDao;
    @Override
    public boolean checkPassoword(Integer userId,String currentPassword) {
        User user = userDao.findById(userId).orElse(null);
        if(user == null){
            return false;
        }else {

            return (user.getPassword().equals(currentPassword)) ? true : false;
        }

    }

    @Override
    public void updatePassword(int userId, String newPassword) {
        userDao.updatePasswordById(userId,newPassword);
    }
}
