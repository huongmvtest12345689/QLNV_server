package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.model.request.ResetByMailRequest;
import co.jp.api.model.request.ResetPasswordRequest;
import co.jp.api.service.ChangePasswordByMailService;
import co.jp.api.util.Messages;
import co.jp.api.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;

@RestController
@RequestMapping("/resetPassword")
public class ChangePasswordByMailController {
    @Autowired
    UserDao userDao;
    @Autowired
    ChangePasswordByMailService changePasswordByMailService;
    @GetMapping("/sendMail")
    public ResourceResponse sendMail(@RequestParam("email") String email){
        System.out.println("email"+email);
        User user = userDao.findByEmail(email);
        if(user!=null){
            String code = changePasswordByMailService.generateCode(email);
            String text = "Your code is : "+ code;
            changePasswordByMailService.setCode(email,code);
            changePasswordByMailService.sendMail(email,"RESET PASSWORD HRM APP",text);
            return new ResourceResponse(Status.STATUS_OK, Messages.SEND_MAIL_OK,user.getId());
        }else{
            System.out.println("null user");
        }
        return new ResourceResponse(Status.STATUS_ERROR, Messages.SEND_MAIL_FAIL);
    }
    @PostMapping("/reset")
    public ResourceResponse resetPassword(@RequestBody ResetPasswordRequest req){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(req.getCode());
        System.out.println(req.getNewPassword());
        Integer userId = req.getId();
        String code = req.getCode();
        String newPassword = encoder.encode(req.getNewPassword());
        if(changePasswordByMailService.checkCode(userId,code)){
            changePasswordByMailService.updatePassword(userId,newPassword);
            return new ResourceResponse(Status.STATUS_OK,Messages.UPDATE_PASSWORD_SUCCESS);
        }else{
            return new ResourceResponse(Status.STATUS_ERROR,Messages.CODE_EXPIRED);
        }

    }


}
