package co.jp.api.exception;

import co.jp.api.cmn.ResourceResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class ExcelHandleException extends Throwable{
    @ExceptionHandler(IOException.class)
    public ResourceResponse handleIOException(Exception ex, WebRequest request) {
        return new ResourceResponse(500,"Không thể xử lý được File.");
    }
}
