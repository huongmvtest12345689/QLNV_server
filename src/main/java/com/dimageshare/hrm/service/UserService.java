package com.dimageshare.hrm.service;

import com.dimageshare.hrm.entity.User;
import com.dimageshare.hrm.exception.NoDataFoundException;
import com.dimageshare.hrm.exception.UserNotFoundException;
import com.dimageshare.hrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User findById(Integer id){
        return userRepository.findById(id).orElse(null);
    }
    public List<User> findAllUsers(){
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
