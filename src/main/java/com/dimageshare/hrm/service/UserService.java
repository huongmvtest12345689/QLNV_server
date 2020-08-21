package com.dimageshare.hrm.service;

import com.dimageshare.hrm.entity.User;
import com.dimageshare.hrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
