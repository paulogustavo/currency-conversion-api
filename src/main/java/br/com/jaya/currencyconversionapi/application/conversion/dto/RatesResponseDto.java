package br.com.jaya.currencyconversionapi.application.conversion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatesResponseDto implements Serializable {

    private Map<String, BigDecimal> rates;


}
