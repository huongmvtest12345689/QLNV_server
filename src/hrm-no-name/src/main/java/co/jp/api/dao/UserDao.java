package co.jp.api.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.jp.api.entity.User;
import co.jp.api.model.response.UserResDto;

@Repository
public interface UserDao {
	HashMap<String, User> getEmailList();
	boolean insertData(List<UserResDto> dataList);
}

