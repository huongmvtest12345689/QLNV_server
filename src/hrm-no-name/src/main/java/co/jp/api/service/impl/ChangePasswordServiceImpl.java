package co.jp.api.service.impl;

import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {
    @Autowired
    UserDao userDao;
    @Override
    public boolean checkPassoword(Integer userId,String currentPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userDao.findById(userId).orElse(null);
        if(user == null){
            return false;
        }else {
            return encoder.matches(currentPassword,user.getPassword());
        }

    }

    @Override
    public void updatePassword(int userId, String newPassword) {
        userDao.updatePasswordById(userId,newPassword);
    }
}
