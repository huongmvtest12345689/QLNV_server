package co.jp.api.exception;

import co.jp.api.cmn.ResponseApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserHandleException extends Throwable{
    @ExceptionHandler(NullPointerException.class)
    public ResponseApi handleNullPointerException(Exception ex, WebRequest request) {
        return new ResponseApi("Khong co User thoa man.");
    }
}
