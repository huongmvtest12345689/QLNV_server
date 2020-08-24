package com.ds.hrm.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Integer id) {
//        super(String.format("Không tồn tại User với Id %d", id));
    }
}
