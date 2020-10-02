package co.jp.api.dao;

import co.jp.api.entity.User;
import co.jp.api.model.request.UserReqDto;
import co.jp.api.model.request.UserUpdateReqDto;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UserDao {
    List<User> findAll();
    User findByEmail(String email);
    User findByCodeAndEmail(String code, String email);
    Boolean forgotPassword(String code, Integer userId, Timestamp code_start, Timestamp code_end);
    Boolean resetPassword(Integer userId, String password, Timestamp code_start, Timestamp code_end);
    Boolean importUserFromFile(List<UserReqDto> userReqDtoList);
    Boolean update(UserUpdateReqDto userUpdateReqDto);
    Boolean delete(String email);
    Boolean deleteMulti(List<String> listEmail);
    Boolean save(UserReqDto userReqDto);
}
