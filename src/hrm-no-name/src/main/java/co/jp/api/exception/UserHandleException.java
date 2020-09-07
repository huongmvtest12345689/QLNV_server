package co.jp.api.exception;

import co.jp.api.cmn.ResourceResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UserHandleException extends Throwable{
    @ExceptionHandler(NullPointerException.class)
    public ResourceResponse handleNullPointerException(Exception ex, WebRequest request) {
        return new ResourceResponse(500,"Khong co User thoa man.");
    }
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResourceResponse handleIndexOutOfBoundsException(Exception ex, WebRequest request){
        return new ResourceResponse(500, "Khong co User thoa man.");
    }
}
