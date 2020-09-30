package co.jp.api.service;

import co.jp.api.entity.User;
import co.jp.api.model.request.ResetPasswordDto;
import co.jp.api.model.request.UserUpdateResDto;
import co.jp.api.model.response.ResetPasswordResDto;
import co.jp.api.model.response.UserAllResDto;

import java.util.List;
import java.util.Map;

public interface UserApiService {
    Boolean importFileExcel(List<User> listUsers);
    List<UserAllResDto> findAll();
    User findByEmail(String email);
    Boolean update(UserUpdateResDto userUpdateResDto);
    User findByCodeAndEmail(String code, String email);
    String forgotPassword(User user);
    ResetPasswordResDto resetPassword(ResetPasswordDto resetPasswordDto);
    Boolean delete(Integer userId);
}
