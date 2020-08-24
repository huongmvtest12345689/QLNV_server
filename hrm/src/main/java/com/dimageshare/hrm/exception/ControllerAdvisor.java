package com.dimageshare.hrm.exception;

import com.dimageshare.hrm.base.ResourceResponse;
import com.dimageshare.hrm.util.Status;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResourceResponse handleUserNotFoundException() {
        return new ResourceResponse(Status.NOT_FOUND, "Khong co User phu hop.");
    }
    @ExceptionHandler(value = NoDataFoundException.class)
    public ResourceResponse handleNodataFoundException() {
        return new ResourceResponse(Status.NOT_FOUND, "Khong co du lieu.");
    }
}
