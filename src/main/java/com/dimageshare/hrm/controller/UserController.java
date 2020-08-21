package com.dimageshare.hrm.controller;

import com.dimageshare.hrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("user", userService.findAllUsers());
        return "index";
    }
}
