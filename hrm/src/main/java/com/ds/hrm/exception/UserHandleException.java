package com.ds.hrm.exception;

import com.ds.hrm.base.ResourceResponse;
import com.ds.hrm.util.Status;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserHandleException extends Throwable{
    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(NullPointerException.class)
    public ResourceResponse handleNullPointerException(Exception ex, WebRequest request) {
        return new ResourceResponse(Status.NOT_FOUND, "Không có dữ liệu về User.");
    }
}
