package co.jp.api.dao;

import co.jp.api.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User findByEmail(String email);
}
