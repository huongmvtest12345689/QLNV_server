package com.dimageshare.hrm.restApi;

import com.dimageshare.hrm.base.ResourceResponse;
import com.dimageshare.hrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/user/")
public class UserRestApi {
    @Autowired
    private UserService userService;
    @GetMapping("")
    public ResourceResponse getUser() {
        return new ResourceResponse(userService.findAllUsers());
    }
}
