package br.com.jaya.currencyconversionapi.domain.conversion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatesResponse {
    private Map<String, BigDecimal> rates;
}
