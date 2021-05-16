package br.com.jaya.currencyconversionapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RequestDTO {

    private String originCurrency;
    private BigDecimal value;
    private String finalCurrency;
    private String userId;

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
