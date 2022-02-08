package br.com.jaya.currencyconversionapi.application.conversion.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyDto {
    USD("USD"),
    AUD("AUD"),
    CAD("CAD"),
    PLN("PLN"),
    MXN("MXN"),
    BRL("BRL"),
    EUR("EUR"),
    JPY("JPY");

    private final String description;

    CurrencyDto(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription(){
        return description;
    }
}
