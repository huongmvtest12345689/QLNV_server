package co.jp.api.util;

import co.jp.api.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageContants {
    public static final String IMPORT_FILE_SUCCESS = "Import File thành công!";
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,8}$g!";
    public static Map<String, List<String>> messageImport = new HashMap<>();
}
