package com.ds.hrm.service;

import com.ds.hrm.entity.User;
import com.ds.hrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){ return userRepository.findByEmail(email); }
    public User findById(Integer id){ return userRepository.findById(id).orElse(null); }
    public List<User> findAllUsers(){
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
