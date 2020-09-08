package co.jp.api.service;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.entity.User;
import co.jp.api.model.response.CountryResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ExcelApiService {
    Map<String, List<User>> saveFileImport(byte[] decodeBase64);
    User findByEmail(String email);
}
