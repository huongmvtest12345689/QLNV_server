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
    public Boolean importUserFromFile(List<UserResDto> userResDtoList) {
        String sql = AppUtils.sqlExcute("cmn/USER_02_INSERT_MULTI_RECORD.sql");
        Query query = this.entityManager.createNativeQuery(sql, User.class);
        for (UserResDto u : userResDtoList) {
            getValueObject(u, query);
            query.executeUpdate();
        }
        return true;
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
