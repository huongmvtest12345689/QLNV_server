package co.jp.api.exception;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.util.AppUtils;
import co.jp.api.util.MessageContants;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

@ControllerAdvice
public class ExcelHandleException extends Throwable{
    @ExceptionHandler(IOException.class)
    public ResourceResponse handleIOException(Exception ex, WebRequest request) {
        return new ResourceResponse(500,MessageContants.MSG_023);
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResourceResponse handleMaxSizeException() {
        return new ResourceResponse(500, MessageContants.MSG_015);
    }
}
