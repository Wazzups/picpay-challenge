package com.wazzups.picpaychallengev2.web.controller;

import com.wazzups.picpaychallengev2.application.exception.ExternalServiceException;
import com.wazzups.picpaychallengev2.application.exception.InsufficientBalanceException;
import com.wazzups.picpaychallengev2.application.exception.UnauthorizedTransferException;
import com.wazzups.picpaychallengev2.application.exception.UserNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound(UserNotFoundException ex){
        return Collections.singletonMap("error", ex.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBalance(InsufficientBalanceException ex){
        return Collections.singletonMap("error", ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedTransferException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleTransfer(UnauthorizedTransferException ex) {
        return Collections.singletonMap("error", ex.getMessage());
    }

    @ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> handleExternalService(ExternalServiceException ex){
        return Collections.singletonMap("error", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        return errors;
    }
}

