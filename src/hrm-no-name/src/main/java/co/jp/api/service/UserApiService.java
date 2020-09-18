package co.jp.api.service;

import co.jp.api.entity.User;
import co.jp.api.model.request.ResetPasswordDto;
import co.jp.api.model.response.ResetPasswordResDto;

import java.util.List;
import java.util.Map;

public interface UserApiService {
    Boolean importFileExcel(List<User> listUsers);
    List<User> findAll();
    User findByEmail(String email);
    User findByCodeAndEmail(String code, String email);
    String forgotPassword(User user);
    ResetPasswordResDto resetPassword(ResetPasswordDto resetPasswordDto);
}
