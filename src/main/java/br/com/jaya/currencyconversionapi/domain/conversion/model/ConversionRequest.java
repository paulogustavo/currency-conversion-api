package br.com.jaya.currencyconversionapi.domain.conversion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ConversionRequest {

    private Currency originCurrency;
    private BigDecimal value;
    private Currency finalCurrency;
    private String userId;

}
