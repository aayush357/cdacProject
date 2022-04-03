package com.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.exception.ActivePackageNotFoundException;
import com.exception.EntityAlreadyPresentException;
import com.exception.EntityNotFoundException;
import com.exception.EntityNullException;
import com.exception.InvalidTokenRequestException;
import com.exception.TokenNotFoundException;
import com.response.ExceptionResponse;

@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleExceptions( EntityNotFoundException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        System.out.println(exception.getMessage()+"handler");
        response.setMessage(exception.getMessage());
        System.out.println(exception.getStatus());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,exception.getStatus());
        System.out.println(entity);
        return entity;
    }
    
    @ExceptionHandler(EntityNullException.class)
    public ResponseEntity<Object> handleExceptions( EntityNullException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,exception.getStatus());
        return entity;
    }
    
    @ExceptionHandler(EntityAlreadyPresentException.class)
    public ResponseEntity<Object> handleExceptions( EntityAlreadyPresentException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,exception.getStatus());
        return entity;
    }
    
    @ExceptionHandler(ActivePackageNotFoundException.class)
    public ResponseEntity<Object> handleExceptions( ActivePackageNotFoundException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,exception.getStatus());
        return entity;
    }
    
    @ExceptionHandler(InvalidTokenRequestException.class)
    public ResponseEntity<Object> handleExceptions( InvalidTokenRequestException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
        return entity;
    }
    
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Object> handleExceptions( TokenNotFoundException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        System.out.println(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
        return entity;
    }
}
