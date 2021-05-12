package br.com.jaya.currencyconversionapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Calendar;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private String id;

    @Setter
    private String userId;

    @Setter
    private String originCurrency;

    @Setter
    private String convertedCurrency;

    @Setter
    private BigDecimal originValue;

    @Setter
    private BigDecimal convertedValue;

    @Setter
    private BigDecimal conversionRate;

    @Setter
    private Calendar createdAt;


}
