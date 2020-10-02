package co.jp.api.service;

import co.jp.api.entity.User;
import co.jp.api.model.request.ResetPasswordDto;
import co.jp.api.model.request.UserReqDto;
import co.jp.api.model.request.UserUpdateReqDto;
import co.jp.api.model.response.ResetPasswordResDto;
import co.jp.api.model.response.UserAllResDto;

import java.util.List;

public interface UserApiService {
    Boolean importFileExcel(List<User> listUsers);
    List<UserAllResDto> findAll();
    User findByEmail(String email);
    Boolean update(UserUpdateReqDto userUpdateReqDto);
    User findByCodeAndEmail(String code, String email);
    String forgotPassword(User user);
    ResetPasswordResDto resetPassword(ResetPasswordDto resetPasswordDto);
    Boolean delete(String email);
    Boolean deleteMulti(List<String> listEmail);
    Boolean save(UserReqDto userReqDto);
}
