package br.com.jaya.currencyconversionapi.infrastructure.exception;

public class CurrencyConversionException extends RuntimeException{
    public CurrencyConversionException(String message) {
        super(message);
    }
}
