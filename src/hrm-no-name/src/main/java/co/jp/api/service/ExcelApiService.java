package co.jp.api.service;

import co.jp.api.entity.User;

public interface ExcelApiService {
    Boolean saveFileImport(byte[] decodeBase64);
    User findByEmail(String email);
}
