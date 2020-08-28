package com.ds.hrm.exception;

import com.ds.hrm.base.ResourceResponse;
import com.ds.hrm.util.Status;
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
