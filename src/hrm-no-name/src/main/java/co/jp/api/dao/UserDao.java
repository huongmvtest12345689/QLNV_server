package co.jp.api.dao;

import co.jp.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.id = ?1")
    void updatePasswordById(int userId,String newPassword);
    @Transactional
    @Modifying
    @Query("update User u set u.token = ?2,u.tokenStart = ?3,u.tokenEnd = ?4 where u.id = ?1")
    void updateToken(int userId, String token, Date tokenStart, Date tokenEnd);
    @Transactional
    @Modifying
    @Query("update User u set u.code = ?2,u.codeStart = ?3,u.codeEnd = ?4 where u.email = ?1")
    void setCode(String email, String code, Date codeStart, Date codeEnd);
    User findByName(String name);
    User findByEmail(String email);
    User findByCode(String code);
}
