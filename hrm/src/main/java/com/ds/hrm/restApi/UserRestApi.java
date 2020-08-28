package com.ds.hrm.restApi;

import com.ds.hrm.base.ResourceResponse;
import com.ds.hrm.entity.User;
import com.ds.hrm.exception.UserHandleException;
import com.ds.hrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user/")
public class UserRestApi {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    @ExceptionHandler(UserHandleException.class)
    public ResourceResponse findById(@PathVariable("id") Integer id){
        User data = userService.findById(id);
        data.getId().toString();
        return new ResourceResponse(data);
    }
}
