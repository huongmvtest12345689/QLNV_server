package co.jp.api.util;

import co.jp.api.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageContants {
    public static final String MSG_014 = "Format file error. Please deleta or upload again.";
    public static final String MSG_015 = "Invalid file size.";
    public static final String MSG_016 = "File error . Lines{0} Column {1} duplicate in Sheet {2}.";
    public static final String MSG_017 = "Duplicate data. Lines{0} Column {1} in Sheet {2} have been exists in table.";
    public static final String MSG_018 = "Upload file successful.";
    public static final String MSG_019 = "Please choose file before upload.";
    public static final String MSG_021 = "File error . Lines{0} Column {1} Invalid in Sheet {2}.";
    public static final String MSG_022 = "File error . Lines{0} Column {1} not BLANK in Sheet {2}.";
    public static final String MSG_023 = "Upload file error. Please upload again.";
    public static Map<String, List<String>> messageImport = new HashMap<>();
}
