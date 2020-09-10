package co.jp.api.dao;

import co.jp.api.entity.User;
import co.jp.api.model.request.UserResDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User findByEmail(String email);
    Boolean importUserFromFile(List<UserResDto> userResDtoList);
}
