package co.jp.api.dao.impl;

import co.jp.api.dao.UserDao;
import co.jp.api.entity.Country;
import co.jp.api.entity.User;
import co.jp.api.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public User findByEmail(String email){
        String sql = AppUtils.sqlExcute("cmn/USER_01_FIND_EMAIL.sql");
        Query query = this.entityManager.createNativeQuery(sql, User.class);

        query.setParameter("email", email);
        List<User> users = query.getResultList();

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }
}
