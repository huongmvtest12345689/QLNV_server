package co.jp.api.util;

public class SecurityConfig {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    public static  final String JWT_SECRET = "hrmApp";

    //Thời gian có hiệu lực của chuỗi jwt
    public static long JWT_EXPIRATION = 604800000L;
}
