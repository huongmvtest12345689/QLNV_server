package co.jp.api.util;

import co.jp.api.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenJWTUtils {

    static final long EXPIRATIONTIME = 86_400_000; // 1 day
    static final String SECRET = "SecretKeyHRM";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public String generateToken(String email) {
        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + EXPIRATIONTIME);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUserNameFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    // Lấy tên email từ token
//    public static String getUsernameFromToken(String token) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claims.getSubject();
//    }
//
//    // Lấy ngày hết hiệu lực của token
//    public Date getExpirationDateFromToken(String token) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claims.getExpiration();
//    }
//
//    // Sử dụng khóa ở trên để giải mã token
//    private static Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
//    }
//
//    // Kiểm tra xem token có còn trong thời gian hiệu lực
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }

//    public String generateJwtToken(Authentication authentication) {
//
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject((userPrincipal.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }

    // Tạo một token để trả về cho người dùng
//    public static String generateToken(User user) {
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, user.getEmail());
//    }

    // Tạo ra môt token chứa các thông tin: username, ngày khởi tạo, ngày hết hiệu lực, khóa
//    private static String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
//                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
//    }

    // Kiểm tra token có hiệu lực
//    public Boolean validateToken(String token, User user) {
//        final String email = getUsernameFromToken(token);
//        return (email.equals(user.getEmail()) && !isTokenExpired(token));
//    }
}
