package br.com.jaya.currencyconversionapi.domain.conversion.service;

import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.RatesRepository;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.TransactionRepository;
import br.com.jaya.currencyconversionapi.domain.user.exception.UserNotFoundException;
import br.com.jaya.currencyconversionapi.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyConversionService {

    UserRepository userRepository;
    RatesRepository ratesRepository;
    TransactionRepository transactionRepository;

    private static final int SCALE = 6;

    /**
     * Convert currency - Domain layer service
     * @param conversionRequest Domain object with conversion needed info
     * @return Mono<Transaction> object with saved transaction
     */
    public Mono<Transaction> convert(ConversionRequest conversionRequest){
        return userRepository.findById(conversionRequest.getUserId())
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .flatMap(user -> ratesRepository.fetchRates())
                .flatMap(rates ->  convertCurrency(rates.getRates(), conversionRequest)
                                .flatMap(Mono::just)
                );
    }

    /**
     * Calculates conversion and invoke method to save transaction
     * @param rates Map mapping String currency to BigDecimal amount
     * @param conversionRequest Domain object with conversion needed info
     * @return Mono<Transaction> object with saved transaction
     */
    private Mono<Transaction> convertCurrency(Map<String, BigDecimal> rates, ConversionRequest conversionRequest) {
        BigDecimal rateEuroToOriginCurrency = rates.get(conversionRequest.getOriginCurrency().getDescription());
        BigDecimal euroValue = conversionRequest.getValue().divide(rateEuroToOriginCurrency, SCALE, RoundingMode.HALF_UP);
        BigDecimal rateEuroToFinalCurrency = rates.get(conversionRequest.getFinalCurrency().getDescription());
        BigDecimal finalValue = euroValue.multiply(rateEuroToFinalCurrency);
        BigDecimal conversionRate = finalValue.divide(conversionRequest.getValue(), RoundingMode.HALF_UP);

        return saveTransaction(conversionRate, conversionRequest, finalValue);
    }

    /**
     * Saves transaction
     * @param conversionRate Conversion rate
     * @param conversionRequest Domain object with conversion needed info
     * @param finalValue Final amount value
     * @return Mono<Transaction> object with saved transaction
     */
    private Mono<Transaction> saveTransaction(BigDecimal conversionRate, ConversionRequest conversionRequest, BigDecimal finalValue){
        var transaction = Transaction.builder()
                .conversionRate(conversionRate)
                .finalCurrency(conversionRequest.getFinalCurrency().getDescription())
                .originValue(conversionRequest.getValue())
                .finalValue(finalValue)
                .originCurrency(conversionRequest.getOriginCurrency().getDescription())
                .userId(conversionRequest.getUserId())
                .createdAt(new Date())
                .build();

        return transactionRepository.save(transaction);
    }
}
