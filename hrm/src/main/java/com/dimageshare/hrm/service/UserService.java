package com.dimageshare.hrm.service;

import com.dimageshare.hrm.dto.UserDTO;
import com.dimageshare.hrm.dto.UserDetailsDTO;
import com.dimageshare.hrm.exception.UserLockException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetailsDTO findUserDetailById(Long id);
    UserDTO getOne(Long id);
    void changePassword(String password, Long id);
    void updateStatusUserLoginRedis(Long id, int status, String type);
    void updateTimeLoginFail(String account) throws UserLockException;
    void sendMailRemindPassword(String email);
    boolean isLogout(String username);
}
