package co.jp.api.dao.impl;

import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.model.request.UserResDto;
import co.jp.api.util.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
        if (!CollectionUtils.isEmpty(users)) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public User findByCodeAndEmail(String code, String email){
        String sql = AppUtils.sqlExcute("cmn/USER_04_FIND_CODE_AND_EMAIL.sql");
        Query query = this.entityManager.createNativeQuery(sql, User.class);
        query.setParameter("code", code);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        if (!CollectionUtils.isEmpty(users)) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public Boolean importUserFromFile(List<UserResDto> userResDtoList) {
        String sql = AppUtils.sqlExcute("cmn/USER_02_INSERT_MULTI_RECORD.sql");
        Query query = this.entityManager.createNativeQuery(sql, User.class);
        for (UserResDto u : userResDtoList) {
            getValueObject(u, query);
            query.executeUpdate();
        }
        return true;
    }

    @Override
    public Boolean forgotPassword(String code, Integer userId, Timestamp code_start, Timestamp code_end){
        String sql = AppUtils.sqlExcute("cmn/USER_06_FORGOT_PASSWORD.sql");
        Query query = this.entityManager.createNativeQuery(sql, User.class);
        query.setParameter("code", code);
        query.setParameter("id", userId);
        query.setParameter("code_start", code_start);
        query.setParameter("code_end", code_end);
        query.executeUpdate();
        return true;
    }

    @Override
    public Boolean resetPassword(Integer userId, String password, Timestamp code_start, Timestamp code_end){
        String sql = AppUtils.sqlExcute("cmn/USER_05_RESET_PASSWORD.sql");
        Query query = this.entityManager.createNativeQuery(sql, User.class);
        query.setParameter("id", userId);
        query.setParameter("password", password);
        query.setParameter("code_end", code_end);
        query.setParameter("code_start", code_start);
        query.executeUpdate();
        return true;
    }

    @Override
    public List<User> findAll() {
        String sql = AppUtils.sqlExcute("cmn/USER_03_SELECT_ALL.sql");
        List<User> userList = this.entityManager.createNativeQuery(sql, User.class).getResultList();
        return userList;
    }

    private void getValueObject (Object ObjectInput, Query query) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> props = objectMapper.convertValue(ObjectInput, Map.class);
        for(Map.Entry<String, Object> entry : props.entrySet()){
            query.setParameter(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + "-->" + entry.getValue());
        }
    }

}
