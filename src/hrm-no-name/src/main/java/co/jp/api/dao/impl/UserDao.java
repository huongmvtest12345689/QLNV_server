package co.jp.api.dao.impl;

import co.jp.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.id = ?1")
    void updatePasswordById(int userId,String newPassword);
}
