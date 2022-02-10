package br.com.jaya.currencyconversionapi.infrastructure.data.exception;

import br.com.jaya.currencyconversionapi.domain.user.exception.UserNotFoundException;
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
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<Object> handleCustomException(InfrastructureException ex) {
        ex.printStackTrace();
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ex.printStackTrace();
        return getResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        ex.printStackTrace();
        return getResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<Object> getResponseEntity(HttpStatus status, String message){
        ApiError apiError = ApiError.builder()
                .code(status.value())
                .message(message)
                .build();
        return new ResponseEntity<>(apiError, status);
    }

}
