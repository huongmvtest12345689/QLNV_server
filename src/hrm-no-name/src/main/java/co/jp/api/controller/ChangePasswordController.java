package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.model.request.ChangePasswordRequest;
import co.jp.api.service.ChangePasswordService;
import co.jp.api.service.impl.ChangePasswordServiceImpl;
import co.jp.api.util.Messages;
import co.jp.api.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ChangePasswordController {
    @Autowired
    ChangePasswordService changePassService;
    @PostMapping("/update")
    public ResourceResponse updatePassword(@RequestBody ChangePasswordRequest req){
        int userId = req.getId();
        String password = req.getPassword();
        String newPassword = req.getNewPassword();
        if(!changePassService.checkPassoword(userId,password)){
            return new ResourceResponse(Status.STATUS_ERROR ,Messages.PASSWORD_NOT_FOUND);
        }else{
//            changePassService.updatePassword(userId,newPassword);
            return new ResourceResponse(Status.STATUS_OK,Messages.UPDATE_PASSWORD_SUCCESS);
        }
    }


}
