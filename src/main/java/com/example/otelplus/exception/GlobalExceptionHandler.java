package com.example.otelplus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Tüm RuntimeException hatalarını (bizim fırlattığımız hatalar bu türden) yakalar
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        // O karmaşık JSON yerine sadece hata mesajını döndürür
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}