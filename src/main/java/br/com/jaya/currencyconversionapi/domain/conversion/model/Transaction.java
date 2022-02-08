package br.com.jaya.currencyconversionapi.domain.conversion.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Builder
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
    private String finalCurrency;

    @Setter
    private BigDecimal originValue;

    @Setter
    private BigDecimal finalValue;

    @Setter
    private BigDecimal conversionRate;

    @Setter
    private Date createdAt;

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", originCurrency='" + originCurrency + '\'' +
                ", finalCurrency='" + finalCurrency + '\'' +
                ", originValue=" + originValue +
                ", finalValue=" + finalValue +
                ", conversionRate=" + conversionRate +
                ", createdAt=" + createdAt +
                '}';
    }
}
