package br.com.jaya.currencyconversionapi.domain.conversion.model;

import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
public class ConversionRequest {

    private Currency originCurrency;
    private BigDecimal value;
    private Currency finalCurrency;
    private String userId;

    public Currency getOriginCurrency() {
        return originCurrency;
    }

    public void setOriginCurrency(Currency originCurrency) {
        this.originCurrency = originCurrency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getFinalCurrency() {
        return finalCurrency;
    }

    public void setFinalCurrency(Currency finalCurrency) {
        this.finalCurrency = finalCurrency;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
