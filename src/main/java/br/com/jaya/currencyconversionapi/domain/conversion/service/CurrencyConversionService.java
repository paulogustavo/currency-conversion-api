package br.com.jaya.currencyconversionapi.domain.conversion.service;

import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyConversionService {
    UserRepository userRepository;
    RatesRepository ratesRepository;
    TransactionRepository transactionRepository;

    public Mono<Transaction> convert(ConversionRequest conversionRequest){
        return userRepository.findById(conversionRequest.getUserId())
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .flatMap(user -> ratesRepository.fetchRates())
                .flatMap(rates -> getKeySetForValidation(rates)
                .flatMap(ketSet -> convertCurrency(rates.getRates(), conversionRequest))
                                .flatMap(transaction -> {
                                    //logger.info("Transaction created: {}", transaction);
                                    return Mono.just(transaction);
                                })
                );
    }

    private Mono<Set<String>> getKeySetForValidation(RatesResponse ratesResponse) {
        Map<String, BigDecimal> rates = ratesResponse.getRates();
        Set<String> keySet = rates.keySet();
        return Mono.just(keySet);
    }

    private Mono<Transaction> convertCurrency(Map<String, BigDecimal> rates, ConversionRequest conversionRequest) {
        BigDecimal rateEuroToOriginCurrency = rates.get(conversionRequest.getOriginCurrency().getDescription());
        BigDecimal euroValue = conversionRequest.getValue().divide(rateEuroToOriginCurrency, 6, RoundingMode.HALF_UP);
        BigDecimal rateEuroToFinalCurrency = rates.get(conversionRequest.getFinalCurrency().getDescription());
        BigDecimal finalValue = euroValue.multiply(rateEuroToFinalCurrency);
        BigDecimal conversionRate = finalValue.divide(conversionRequest.getValue(), RoundingMode.HALF_UP);

        return saveTransaction(conversionRate, conversionRequest, finalValue);
    }

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
