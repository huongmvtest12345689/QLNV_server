package co.jp.api.controller;

import co.jp.api.cmn.Constants;
import co.jp.api.cmn.ResourceResponse;
import co.jp.api.entity.User;
import co.jp.api.model.CellInfoDTO;
import co.jp.api.model.request.LoginResDto;
import co.jp.api.model.request.ResetPasswordDto;
import co.jp.api.model.response.ResetPasswordResDto;
import co.jp.api.model.response.UserInfoResDto;
import co.jp.api.service.UserApiService;
import co.jp.api.util.CookieUtil;
import co.jp.api.util.TokenJWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
public class UserApiController {

    @Autowired
    private UserApiService userApiService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenJWTUtils tokenJWTUtils;

    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "SecretKeyHRM";
    private static final Map<String, String> credentials = new HashMap<>();

    @PostMapping("login")
    public ResourceResponse authenticateUser(@Valid @RequestBody LoginResDto loginRequest, HttpServletResponse httpServletResponse) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User userLogin = userApiService.findByEmail(loginRequest.getEmail());
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Trả về jwt cho người dùng.
        String jwt = tokenJWTUtils.generateToken(SecurityContextHolder.getContext().getAuthentication().getName());
        Cookie cookie = CookieUtil.createCookie(httpServletResponse, jwtTokenCookieName, jwt, false, 86400, "localhost");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jwt",jwt);
        map.put("cookie",cookie);
        map.put("user",userLogin);
        return new ResourceResponse(200, "Đăng nhập thành công", map);
    }

    @GetMapping("info-login")
    public ResourceResponse getInfoLogin(HttpServletRequest httpServletRequest) {
        String jwt = CookieUtil.getValueCookie(httpServletRequest, jwtTokenCookieName);
        String email = tokenJWTUtils.getUserNameFromJwtToken(jwt);
        User userLogin = userApiService.findByEmail(email);
        UserInfoResDto userInfo = new UserInfoResDto();
        userInfo.setName(userLogin.getName());
        userInfo.setEmail(userLogin.getEmail());
        userInfo.setRoleName(Constants.ROLE_NAME.get(userLogin.getRolesId()));
        return new ResourceResponse(200, "thong tin user", userInfo);
    }

    @GetMapping("random")
    public ResourceResponse randomStuff(){
        return new ResourceResponse("JWT Hợp lệ mới có thể thấy được message này");
    }

    @PostMapping("forgot-password")
    public ResourceResponse forgotPassword(@RequestParam("email") String email) {
        User user = userApiService.findByEmail(email);
        if (email.isEmpty() || email == null || user == null) {
            return new ResourceResponse(404, "Email chưa được đăng ký");
        }
        String result = userApiService.forgotPassword(user);
        return new ResourceResponse(200, "Kiểm tra email để lấy lại mật khẩu", result);
    }
    @PutMapping("reset-password")
    public ResourceResponse resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        ResetPasswordResDto resetPasswordResDto = userApiService.resetPassword(resetPasswordDto);
        return new ResourceResponse(resetPasswordResDto.getStatus(), resetPasswordResDto.getMsg());
    }

    @GetMapping("/logout")
    public ResourceResponse logout(HttpServletResponse httpServletResponse) {
        Cookie cookie = CookieUtil.clearCookie(httpServletResponse, jwtTokenCookieName);
        return new ResourceResponse(200, "logout success", cookie);
    }
}