package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.model.request.ResetByMailRequest;
import co.jp.api.service.ChangePasswordByMailService;
import co.jp.api.util.Messages;
import co.jp.api.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;

@RestController
@RequestMapping("")
public class ChangePasswordByMailController {
    @Autowired
    UserDao userDao;
    @Autowired
    ChangePasswordByMailService changePasswordByMailService;
    @PostMapping("/changePasswordByMail")
    public ResourceResponse changePasswordByMail(@RequestBody ResetByMailRequest request){
        System.out.println(request.getEmail());
        User user = userDao.findByEmail(request.getEmail());
        if(user!=null){
            String code = changePasswordByMailService.generateCode(request.getEmail());
            String text = "Your code is : "+ code;
            changePasswordByMailService.setCode(request.getEmail(),code);
            changePasswordByMailService.sendMail(request.getEmail(),"RESET PASSWORD HRM APP",text);
            return new ResourceResponse(Status.STATUS_OK, Messages.UPDATE_PASSWORD_SUCCESS);
        }else{
            System.out.println("null user");
        }
        return new ResourceResponse(Status.STATUS_ERROR, Messages.PASSWORD_NOT_FOUND);
    }

}
