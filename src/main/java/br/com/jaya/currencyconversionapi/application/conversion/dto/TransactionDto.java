package br.com.jaya.currencyconversionapi.application.conversion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDto {

    @JsonProperty("identifier")
    String id;

    @JsonProperty("userIdenfier")
    String userId;

    String originCurrency;

    String finalCurrency;

    BigDecimal originValue;

    BigDecimal finalValue;

    BigDecimal conversionRate;

    Date createdAt;

}
