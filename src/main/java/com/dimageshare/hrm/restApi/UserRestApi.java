package com.dimageshare.hrm.restApi;

import com.dimageshare.hrm.base.ResourceResponse;
import com.dimageshare.hrm.entity.User;
import com.dimageshare.hrm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user/")
public class UserRestApi {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserRestApi.class);
    @GetMapping("")
    public ResourceResponse getUser() {
        List<User> data = userService.findAllUsers();
        logger.debug("Hello from Logback {}", data);
        return new ResourceResponse(userService.findAllUsers());
    }
}
