package co.jp.api.service.impl;

import co.jp.api.cmn.ExcelHelper;
import co.jp.api.cmn.ResourceResponse;
import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.service.ExcelApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExcelApiServiceImpl implements ExcelApiService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ExcelHelper excelHelper;
    public Map<String, List<User>> saveFileImport(byte[] decodeBase64){
        InputStream is = new ByteArrayInputStream(decodeBase64);
        Map<String, List<User>> userList = new HashMap<>();
        try {
            userList = excelHelper.mapDataObject(is);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return userList;
    }
    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }
}
