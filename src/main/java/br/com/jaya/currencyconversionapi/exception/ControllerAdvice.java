package br.com.jaya.currencyconversionapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleNonCachedException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>( "Falha ao realizar conversão", HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler(CurrencyConversionException.class)
    public ResponseEntity<Object> handleCustomException(CurrencyConversionException exception) {
        return new ResponseEntity<>( exception.getMessage(), HttpStatus.BAD_REQUEST );
    }

}
