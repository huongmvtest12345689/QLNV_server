package co.jp.api.service;

import co.jp.api.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

public interface ChangePasswordService {
    boolean checkPassoword(Integer userId,String password);

    void updatePassword(int userId, String newPassword);
}
