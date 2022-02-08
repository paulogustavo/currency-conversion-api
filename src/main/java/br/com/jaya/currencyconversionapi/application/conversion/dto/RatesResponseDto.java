package br.com.jaya.currencyconversionapi.application.conversion.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatesResponseDto {

    Map<String, BigDecimal> rates;

}
