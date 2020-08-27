package com.dimageshare.hrm.controller;

import com.dimageshare.hrm.dto.LoginDTO;
import com.dimageshare.hrm.dto.ResetPasswordDTO;
import com.dimageshare.hrm.dto.ResponseDTO;
import com.dimageshare.hrm.dto.UserDetailsDTO;
import com.dimageshare.hrm.exception.UserLockException;
import com.dimageshare.hrm.filter.JwtTokenProvider;
import com.dimageshare.hrm.service.UserService;
import com.dimageshare.hrm.util.Constants;
import com.dimageshare.hrm.util.Translator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    @PostMapping(value = "/login")
    public ResponseEntity<?> getStringAuth(@Valid @RequestBody LoginDTO loginDTO) {
        Authentication authentication;
        try {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getAccount(),
                            loginDTO.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            userService.updateTimeLoginFail(loginDTO.getAccount());
            throw e;
        }

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(userDetailsDTO);
        userService.updateStatusUserLoginRedis(userDetailsDTO.getUserDTO().getId(), 0, "block_");
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, jwt)
                .body(new ResponseDTO(Translator.translate(Constants.SUCCESS), null));
    }

    @GetMapping(value = "/get-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(){
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new ResponseDTO(Translator.translate(Constants.SUCCESS), userService.getOne(userDetailsDTO.getUserDTO().getId())));
    }

    @PutMapping(value = "/change-pass", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO){
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userDetailsDTO.getUserDTO().getPassword().equals(passwordEncoder.encode(resetPasswordDTO.getOldPassword()))) {
            return ResponseEntity.badRequest().body(new ResponseDTO(Translator.translate("wrong.password"), null));
        }
        userService.changePassword(resetPasswordDTO.getNewPassword(), userDetailsDTO.getUserDTO().getId());
        return ResponseEntity.ok(new ResponseDTO(Translator.translate(Constants.SUCCESS), null));
    }

    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(){
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateStatusUserLoginRedis(userDetailsDTO.getUserDTO().getId(), 1, "block_");
        return ResponseEntity.ok(new ResponseDTO(Translator.translate(Constants.SUCCESS), null));
    }

    @PostMapping(value = "/remind-password")
    public ResponseEntity<?> logout(Map<String, String> payload) {
        String email = payload.get("email");
        if(!StringUtils.isEmpty(email)) {
            userService.sendMailRemindPassword(email);
        }
        return ResponseEntity.ok(new ResponseDTO(Translator.translate(Constants.SUCCESS), null));
    }
}
