package com.ds.hrm.restApi;

import com.ds.hrm.base.ResourceResponse;
import com.ds.hrm.entity.User;
import com.ds.hrm.exception.NoDataFoundException;
import com.ds.hrm.exception.UserNotFoundException;
import com.ds.hrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user/")
public class UserRestApi {
    @Autowired
    private UserService userService;
    @GetMapping("")
    public ResourceResponse getUser() {
        List<User> data = userService.findAllUsers();
        if (data.isEmpty()) {
            throw new NoDataFoundException();
        }
        return new ResourceResponse(userService.findAllUsers());
    }
    @GetMapping("{id}")
    public ResourceResponse findByAllUser(@PathVariable("id") Integer id) {
        User data = userService.findById(id);
        if (data == null) {
            throw new UserNotFoundException(id);
        }
        return new ResourceResponse(data);
    }
}
