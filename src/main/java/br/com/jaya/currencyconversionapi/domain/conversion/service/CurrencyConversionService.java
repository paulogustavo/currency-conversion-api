package br.com.jaya.currencyconversionapi.domain.conversion.service;

import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.ConversionRepository;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.RatesRepository;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.TransactionRepository;
import br.com.jaya.currencyconversionapi.domain.user.repository.UserRepository;
import br.com.jaya.currencyconversionapi.infrastructure.exception.CurrencyConversionException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static br.com.jaya.currencyconversionapi.infrastructure.repository.RatesRepositoryImpl.SYMBOLS;


@Service
public class CurrencyConversionService {
    private ConversionRepository conversionRepository;
    private UserRepository userRepository;
    private RatesRepository ratesRepository;
    private TransactionRepository transactionRepository;

    public Mono<Transaction> convert(ConversionRequest conversionRequest){
        return userRepository.findById(conversionRequest.getUserId())
                .switchIfEmpty(Mono.error(new CurrencyConversionException("User not found")))
                .flatMap(user -> ratesRepository.fetchRates())
                .flatMap(rates -> getKeySetForValidation(rates)
                .flatMap(currenciesKeySet ->
                        validatePresenceOfInformedCurrencies(conversionRequest.getOriginCurrency(),
                                conversionRequest.getFinalCurrency(), currenciesKeySet)
                                .doOnError(Mono::error)
                                .flatMap(ketSet -> convertSavingTransaction(rates.getRates(), conversionRequest))
                                .flatMap(transaction -> {
                                    //logger.info("Transaction created: {}", transaction);
                                    return Mono.just(transaction);
                                }))
                );
        //return conversionRepository.convert(conversionRequest);
    }

    private Mono<Set<String>> validatePresenceOfInformedCurrencies(String originCurrency, String finalCurrency, Set<String> keySet) {
        if (keySet.stream().noneMatch(currency -> currency.equalsIgnoreCase(originCurrency))) {
            return Mono.error(new CurrencyConversionException("Origin currency not found. Only " + SYMBOLS + " permitted."));
        }

        if (keySet.stream().noneMatch(currency -> currency.equalsIgnoreCase(finalCurrency))) {
            return Mono.error(new CurrencyConversionException("Final currency not found. Only " + SYMBOLS + " permitted."));
        }

        return Mono.just(keySet);
    }

    private Mono<Set<String>> getKeySetForValidation(RatesResponse ratesResponseDTO) {
        Map<String, BigDecimal> rates = ratesResponseDTO.getRates();
        Set<String> keySet = rates.keySet();
        return Mono.just(keySet);
    }

    private Mono<Transaction> convertSavingTransaction(Map<String, BigDecimal> rates, ConversionRequest conversionRequest) {
        BigDecimal rateEuroToOriginCurrency = rates.get(conversionRequest.getOriginCurrency());
        BigDecimal euroValue = conversionRequest.getValue().divide(rateEuroToOriginCurrency, 6, RoundingMode.HALF_UP);
        BigDecimal rateEuroToFinalCurrency = rates.get(conversionRequest.getFinalCurrency());
        BigDecimal finalValue = euroValue.multiply(rateEuroToFinalCurrency);
        BigDecimal conversionRate = finalValue.divide(conversionRequest.getValue(), RoundingMode.HALF_UP);

        var transaction = Transaction.builder()
                .conversionRate(conversionRate)
                .finalCurrency(conversionRequest.getFinalCurrency())
                .originValue(conversionRequest.getValue())
                .finalValue(finalValue)
                .originCurrency(conversionRequest.getOriginCurrency())
                .userId(conversionRequest.getUserId())
                .createdAt(new Date())
                .build();

        return transactionRepository.save(transaction);
    }
}
