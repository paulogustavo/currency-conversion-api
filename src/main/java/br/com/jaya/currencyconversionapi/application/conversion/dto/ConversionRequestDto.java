package br.com.jaya.currencyconversionapi.application.conversion.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversionRequestDto {

    @NotNull(message = "Inform origin currency")
    CurrencyDto originCurrency;

    @NotNull(message = "Inform amount to be converted")
    @Positive(message = "Inform a positive number")
    BigDecimal value;

    @NotNull(message = "Inform final currency")
    CurrencyDto finalCurrency;

    @NotNull(message = "Inform user id")
    String userId;

}
