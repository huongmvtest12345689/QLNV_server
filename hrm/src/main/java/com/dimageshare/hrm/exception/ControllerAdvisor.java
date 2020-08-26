package com.dimageshare.hrm.exception;

import com.dimageshare.hrm.dto.ResponseDTO;
import com.dimageshare.hrm.service.UserService;
import com.dimageshare.hrm.util.Constants;
import com.dimageshare.hrm.util.Translator;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Autowired
    private UserService userService;

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticateException(AuthenticationException ex) {
        ResponseDTO responseDTO = new ResponseDTO(Translator.translate("login.info.not.correct"), null);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserLockException.class)
    public ResponseEntity<Object> handleLoginFailOvertimeException(UserLockException ex) {
        ResponseDTO responseDTO = new ResponseDTO(Translator.translate("account.locked"), null);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleValidateException(AccessDeniedException ex) {
        ResponseDTO responseDTO = new ResponseDTO(ex.getMessage(), null);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolations : ex.getConstraintViolations()) {
            errors.add(Translator.translate(constraintViolations.getMessage()));
        }
        ResponseDTO responseDTO = new ResponseDTO(String.join("|", errors), null);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<Object> handleNoSuchMessageException(NoSuchMessageException ex) {
        ResponseDTO responseDTO = new ResponseDTO(Translator.translate(Constants.SYSTEM_ERROR), null);
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(Translator.translate(Objects.requireNonNull(error.getDefaultMessage())));
        }
        ResponseDTO responseDTO = new ResponseDTO(String.join("|", errors), null);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return returnSystemError();

    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(Translator.translate("input.type.missmatch"), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnCatchException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        ResponseDTO responseDTO = new ResponseDTO(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleParseException();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleParseException() {
        ResponseDTO responseDTO = new ResponseDTO(Translator.translate("invalid.format.input"), null);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    private ResponseEntity<Object> handleInputNotCorrectForm() {
        ResponseDTO responseDTO = new ResponseDTO(Translator.translate("request.not.valid"), null);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInputNotCorrectForm();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInputNotCorrectForm();
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInputNotCorrectForm();
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInputNotCorrectForm();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInputNotCorrectForm();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInputNotCorrectForm();
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInputNotCorrectForm();
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInputNotCorrectForm();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> handleMultipartException() {
        return handleInputNotCorrectForm();
    }

    private ResponseEntity<Object> returnSystemError() {
        ResponseDTO responseDTO = new ResponseDTO(Translator.translate(Constants.SYSTEM_ERROR), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }

}
