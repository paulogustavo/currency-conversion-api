package br.com.jaya.currencyconversionapi.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleNonCachedException(Exception ex) {
        ex.printStackTrace();
        var apiError = ApiError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal error")
                .build();
        return new ResponseEntity<>( apiError, HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler(CurrencyConversionException.class)
    public ResponseEntity<Object> handleCustomException(CurrencyConversionException ex) {
        var apiError = ApiError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>( apiError, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        var apiError = ApiError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>( apiError, HttpStatus.BAD_REQUEST );
    }

}
