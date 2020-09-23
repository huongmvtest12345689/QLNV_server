package co.jp.api.controller.auth;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.jwt.CustomUserDetails;
import co.jp.api.jwt.JwtTokenProvider;
import co.jp.api.model.request.LoginRequest;

import co.jp.api.model.request.RegisterRequest;
import co.jp.api.model.response.UserRestDto;
import co.jp.api.util.Messages;
import co.jp.api.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class AuthController{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserDao userDao;
    @PostMapping("/login")
    public ResourceResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        }catch (Exception e){
            return new ResourceResponse(Status.STATUS_ERROR,Messages.LOGIN_FAIL);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(userDetails);
        UserRestDto res = tokenProvider.getUserResDtoFromJWT(jwt);
        userDao.updateToken(res.getId(),res.getToken(),res.getTokenStart(),res.getTokenEnd());
        return new ResourceResponse(Status.STATUS_OK, Messages.LOGIN_SUCCESS,res);
    }
    @PostMapping("/register")
    public ResourceResponse register(@RequestBody RegisterRequest registerRequest) {
        try{
            System.out.println(registerRequest.getName());
            System.out.println(registerRequest.getDisplay_order());
            System.out.println(registerRequest.getEmail());
            System.out.println(registerRequest.getPassword());
            System.out.println(registerRequest.getPhone());
            Date now = new Date();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(registerRequest.getName(),registerRequest.getPhone(),registerRequest.getEmail(),encoder.encode(registerRequest.getPassword()),new Timestamp(now.getTime()),0,0,1);
            user.setRolesId(1);
            userDao.save(user);

        }catch (Exception e){
            System.out.println(e);
            return new ResourceResponse(Status.STATUS_ERROR,Messages.REGISTER_FAIL);
        }
        return new ResourceResponse(Status.STATUS_OK,Messages.REGISTER_SUCCESS);
    }
}