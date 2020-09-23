package co.jp.api.util;

import co.jp.api.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private TokenJWTUtils tokenJWTUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "SecretKeyHRM";

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            String jwt = getJwtFromRequest(request);
//
//            if (jwt != null && tokenJWTUtils.validateToken(jwt)) {
//                String username = tokenJWTUtils.getUserNameFromJwtToken(jwt);
//
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken
//                        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception e) {
//            logger.error("Cannot set user authentication: {}", e);
//        }
//
//        filterChain.doFilter(request, response);
//    }
    private final List<String> allowedOrigins = Arrays.asList("http://localhost:8081");
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        if (httpServletRequest instanceof HttpServletRequest && httpServletResponse instanceof HttpServletResponse) {
//            HttpServletRequest request = (HttpServletRequest) httpServletRequest;
//            HttpServletResponse response = (HttpServletResponse) httpServletResponse;
//
//            // Access-Control-Allow-Origin
//            String origin = request.getHeader("Origin");
//            response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
//            response.setHeader("Vary", "Origin");
//
//            // Access-Control-Max-Age
//            response.setHeader("Access-Control-Max-Age", "3600");
//
//            // Access-Control-Allow-Credentials
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//
//            // Access-Control-Allow-Methods
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//
//            // Access-Control-Allow-Headers
//            response.setHeader("Access-Control-Allow-Headers",
//                    "Origin, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN");
//        }

        String username = getSubject(httpServletRequest, jwtTokenCookieName, signingKey);
        if(username == null){
//            String authService = this.getFilterConfig().getInitParameter("services.auth");
//            httpServletResponse.sendRedirect(authService + "?redirect=" + httpServletRequest.getRequestURL());
            System.out.println("null");
        } else{
            try {
                String jwt = CookieUtil.getValueCookie(httpServletRequest, jwtTokenCookieName);

                if (jwt != null && tokenJWTUtils.validateToken(jwt)) {
                    String email = tokenJWTUtils.getUserNameFromJwtToken(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e);
            }
        }
        httpServletRequest.setAttribute("username", username);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static String getSubject(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
        String token = CookieUtil.getValueCookie(httpServletRequest, jwtTokenCookieName);
        if(token == null) return null;
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
    }
}
