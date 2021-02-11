package com.example.demo.controller;

import com.example.demo.securityAndErrorHandling.ErrorMessage;
import com.example.demo.securityAndErrorHandling.TimeMappingExceptions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class TimeMappingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TimeMappingExceptions.class)
    public ResponseEntity<ErrorMessage> handleExceptions(TimeMappingExceptions e) {
        ErrorMessage error = new ErrorMessage();
        error.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleExceptions(Exception e) {
        e.printStackTrace();
        ErrorMessage error = new ErrorMessage();
        error.setErrorMessage("Internal server error. Try again later.");
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}