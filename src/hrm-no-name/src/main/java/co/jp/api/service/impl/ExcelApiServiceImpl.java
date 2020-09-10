package co.jp.api.service.impl;

import co.jp.api.cmn.ExcelHelper;
import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.service.ExcelApiService;
import co.jp.api.service.UserApiService;
import co.jp.api.util.MessageContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExcelApiServiceImpl implements ExcelApiService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserApiService userApiService;
    @Autowired
    private ExcelHelper excelHelper;
    public Boolean saveFileImport(byte[] decodeBase64){
        InputStream is = new ByteArrayInputStream(decodeBase64);
        Map<String, List<User>> mapUsers = new HashMap<>();
        try {
            mapUsers = excelHelper.mapDataObject(is);

            for (Map.Entry<String, List<User>> mapUser : mapUsers.entrySet()) {
                String key = mapUser.getKey();
                List<User> userList = mapUser.getValue();
                if (MessageContants.messageImport.get(key).size() == 0) {
                    userApiService.importFileExcel(userList);
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return true;
    }
    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }
}
