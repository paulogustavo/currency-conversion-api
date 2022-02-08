package br.com.jaya.currencyconversionapi.domain.conversion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
public class ConversionRequest {

    private String originCurrency;
    private BigDecimal value;
    private String finalCurrency;
    private String userId;

    public String getOriginCurrency() {
        return originCurrency;
    }

    public void setOriginCurrency(String originCurrency) {
        this.originCurrency = originCurrency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getFinalCurrency() {
        return finalCurrency;
    }

    public void setFinalCurrency(String finalCurrency) {
        this.finalCurrency = finalCurrency;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "originCurrency='" + originCurrency + '\'' +
                ", value=" + value +
                ", finalCurrency='" + finalCurrency + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
