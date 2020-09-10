package co.jp.api.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.model.response.UserResDto;
import co.jp.api.util.AppUtils;

@Repository
public class UserDaoImpl  implements UserDao{

	@Autowired
    private EntityManager entityManager;

	@Override
	public HashMap<String, User> getEmailList() {
		List<User> userList = new ArrayList<User>();
		HashMap<String, User> userMap = new HashMap<>();
		String sql = AppUtils.sqlExcute("cmn/USER_01_SELECT_ALL.sql");
		userList = this.entityManager.createNativeQuery(sql, User.class).getResultList();
		if(userList.size() > 0) {
			for (User user : userList) {
				userMap.put(user.getEmail(), user);
			}
		}
		return userMap;
	}

	@Override
	public boolean insertData(List<UserResDto> dataList) {
		int output = 0;
		String query = "INSERT INTO user(name, email, password, roles_id, status) VALUE (?, ?, ?, ?, ?);";
		String insertData = "";
		for (UserResDto user : dataList) {
			String test = this.entityManager.createNativeQuery(query)
					.setParameter("name", user.getName())
					.setParameter("email", user.getEmail())
					.setParameter("password", user.getPassword())
					.setParameter("roles_id", user.getRoles())
					.setParameter("status", user.getStatus())
					.toString();
			insertData = insertData.concat(test);
		}
		output = this.entityManager.createNativeQuery(insertData).executeUpdate();
		if(output > 0) {
			return true;
		}else {
			return false;
		}
		
	}

}
