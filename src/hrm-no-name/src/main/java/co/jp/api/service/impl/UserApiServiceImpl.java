package co.jp.api.service.impl;

import co.jp.api.cmn.Constants;
import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.model.EmailDTO;
import co.jp.api.model.request.ResetPasswordDto;
import co.jp.api.model.request.UserResDto;
import co.jp.api.model.response.ResetPasswordResDto;
import co.jp.api.service.MailApiService;
import co.jp.api.service.UserApiService;
import co.jp.api.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserApiServiceImpl implements UserApiService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MailApiService mailApiService;

    public Boolean importFileExcel(List<User> listUsers){
        List<UserResDto> userResDtoList = new ArrayList<>();
        for (User user : listUsers) {
            UserResDto userResDto = new UserResDto();
            userResDto.setName(user.getName());
            userResDto.setEmail(user.getEmail());
            userResDto.setPassword(user.getPassword());
            userResDto.setRolesId(user.getRolesId());
            userResDto.setStatus(user.getStatus());
            userResDto.setDisplayOrder((long) 0);
            userResDtoList.add(userResDto);
        }
        return userDao.importUserFromFile(userResDtoList);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User findByCodeAndEmail(String code, String email) { return userDao.findByCodeAndEmail(code, email); }

    public String forgotPassword(User user) {
        user.setCode(AppUtils.randomString(6));
        user.setCodeStart(Timestamp.valueOf(LocalDateTime.now()));
        user.setCodeEnd(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));

        userDao.forgotPassword(user.getCode(), user.getId(), user.getCodeStart(), user.getCodeEnd());

        String response = "http://localhost:8081/forgot-password";
        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("code", user.getCode());
        context.setVariable("response", response);
        EmailDTO emailDto = new EmailDTO(user.getEmail(), "Lấy lại mật khẩu", "forgot-password", context);
        mailApiService.sendMail(emailDto);
        return response;
    }

    public ResetPasswordResDto resetPassword(ResetPasswordDto resetPasswordDto) {
        ResetPasswordResDto resetPasswordResDto = new ResetPasswordResDto();
        User user = findByCodeAndEmail(resetPasswordDto.getCode(), resetPasswordDto.getEmail());
        if (user == null) {
            resetPasswordResDto.setStatus(300);
            resetPasswordResDto.setMsg("Mã code hoặc Email không đúng.");
            return resetPasswordResDto;
        }
/*        if (!AppUtils.checkCurrentPassword(resetPasswordDto.getPasswordCurrent(), user.getPassword())) {
            return "Password current not found.";
        }*/
        Timestamp codeStart = user.getCodeStart();
        LocalDateTime now = LocalDateTime.now();
        if (isTokenExpired(codeStart.toLocalDateTime(), now)) {
            resetPasswordResDto.setStatus(300);
            resetPasswordResDto.setMsg("Mã code đã hết thời hạn.");
            return resetPasswordResDto;
        }
        user.setPassword(AppUtils.encode(resetPasswordDto.getNewPass()));

        userDao.resetPassword(user.getId(), user.getPassword(), Timestamp.valueOf(now), Timestamp.valueOf(now.plusHours(1)));
        resetPasswordResDto.setStatus(200);
        resetPasswordResDto.setMsg("Bạn đã cập nhật password thành công.");
        return resetPasswordResDto;
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    /**
     * Check whether the created token expired or not.
     *
     * @param tokenCreationDate
     * @return true or false
     */
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate, final LocalDateTime now) {

        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toHours() >= Constants.EXPIRE_TOKEN_AFTER_HOURS;
    }
}
