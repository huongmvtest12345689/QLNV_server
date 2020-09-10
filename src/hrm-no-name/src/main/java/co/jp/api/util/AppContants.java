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
	
	//HongTT
	public static String ISBLANK = "is blank";
	public static String ERROR_LENGTH = "error length";
	public static String INVALID_EMAIL = "invalid email";
	public static String INVALID_PASSWORD = "invalid password";
	public static String INVALID_ROLES = "invalid roles";
	public static String INVALID_STATUS = "invalid status";
	public static String DUPLICATE = "duplicate";
	public static String DUPLICATE_DB = "duplicate db";
	public static String SUCCESSFUL = "Upload file successful.";
	public static String FILE_ERROR = "Upload file error. Please delete or upload again.";

}
