package br.com.jaya.currencyconversionapi.domain.conversion.model;

public enum Currency {
    USD("USD"),
    AUD("AUD"),
    CAD("CAD"),
    PLN("PLN"),
    MXN("MXN"),
    BRL("BRL"),
    EUR("EUR"),
    JPY("JPY");

    private final String description;

    Currency(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
