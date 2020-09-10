package co.jp.api.util;

import java.nio.file.Paths;

public class AppContants {
	public static String PATH_URL = Paths.get("").toAbsolutePath().toString();
	public static String STRING_SPACE = "";
	public static String STRING_DASH = "/";
	public static String YYYY_MM_DD = "yyyy-mm-dd";
	public static String PATH_RESOURCES = "/src/main/resources/";
	public static String PATH_IMAGES = "/src/main/resources/images";
	public static String PATH_SQL = "/src/main/resources/static/sql/";
	public static String PATH_CONFIG = "/src/main/resources/config/excel/";
	public static String PATH_N = "\n";
	public static String PATH_EMAIL = "/src/main/resources/static/images/email/";
	public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,8}$";
	public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
}
