package com.example.geolocator.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionTranslator {

    void logError(String method, Exception ex) {
        log.error("{}({}) with cause: {} and exception: {}", method, ex.getClass(), ex.getCause(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationFail(MethodArgumentNotValidException ex) {
        logError("processValidationFail", ex);
        ErrorResponse response = new ErrorResponse();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            response.add(error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(response);
    }
}
