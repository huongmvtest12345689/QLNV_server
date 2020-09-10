package co.jp.api.service;

import co.jp.api.entity.User;

import java.util.List;
import java.util.Map;

public interface UserApiService {
    Boolean importFileExcel(List<User> listUsers);
}
