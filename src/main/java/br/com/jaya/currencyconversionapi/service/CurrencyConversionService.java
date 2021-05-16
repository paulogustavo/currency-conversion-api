package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.domain.dto.RatesResponseDTO;
import br.com.jaya.currencyconversionapi.domain.dto.RequestDTO;
import br.com.jaya.currencyconversionapi.exception.CurrencyConversionException;
import br.com.jaya.currencyconversionapi.domain.Transaction;
import br.com.jaya.currencyconversionapi.repository.TransactionRepository;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static br.com.jaya.currencyconversionapi.service.RatesService.SYMBOLS;

@Service
public class CurrencyConversionService {

    Logger logger = LoggerFactory.getLogger(CurrencyConversionService.class);

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final RatesService ratesService;

    public CurrencyConversionService(TransactionRepository transactionRepository,
                                     UserRepository userRepository,
                                     RatesService ratesService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.ratesService = ratesService;
    }

    public Mono<Transaction> convert(RequestDTO requestDTO) {
        return validateInput(requestDTO)
                .doOnError(ex -> {
                    logger.error("{}. Data sent: {}", ex.getMessage(), requestDTO);
                    Mono.error(ex);
                })
                .flatMap(validUserId -> userRepository.findById(validUserId)
                        .switchIfEmpty(Mono.error(new CurrencyConversionException("User not found")))
                        .flatMap(user ->
                                ratesService.fetchRates()
                                        .flatMap(ratesDTO -> getKeySetForValidation(ratesDTO)
                                                .flatMap(currenciesKeySet ->
                                                        validatePresenceOfInformedCurrencies(requestDTO.getOriginCurrency(),
                                                                requestDTO.getFinalCurrency(), currenciesKeySet)
                                                                .doOnError(Mono::error)
                                                                .flatMap(ketSet -> convertSavingTransaction(ratesDTO.getRates(), requestDTO)
                                                                        .flatMap(transaction -> {
                                                                            logger.info("Transaction created: {}", transaction);
                                                                            return Mono.just(transaction);
                                                                        })))


                                        )));
    }

    private Mono<String> validateInput(RequestDTO requestDTO) {
        if (requestDTO.getValue() == null) {
            return Mono.error(new CurrencyConversionException("Value informed should not be null"));
        }
        if (requestDTO.getValue().compareTo(BigDecimal.ZERO) < 1) {
            return Mono.error(new CurrencyConversionException("Value informed should be greater than zero"));
        }
        if (requestDTO.getOriginCurrency() == null) {
            return Mono.error(new CurrencyConversionException("Origin currency should not be null"));
        }
        if (requestDTO.getFinalCurrency() == null) {
            return Mono.error(new CurrencyConversionException("Final currency should not be null"));
        }
        if (requestDTO.getOriginCurrency().isEmpty()) {
            return Mono.error(new CurrencyConversionException("Inform origin currency"));
        }
        if (requestDTO.getFinalCurrency().isEmpty()) {
            return Mono.error(new CurrencyConversionException("Inform final currency"));
        }
        if (requestDTO.getUserId() == null) {
            return Mono.error(new CurrencyConversionException("User id should not be null"));
        }
        if (requestDTO.getOriginCurrency().equalsIgnoreCase(requestDTO.getFinalCurrency())) {
            return Mono.error(new CurrencyConversionException("Origin and final currencies should not be equal"));
        }

        return Mono.just(requestDTO.getUserId());
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

    private Mono<Set<String>> getKeySetForValidation(RatesResponseDTO ratesResponseDTO) {
        Map<String, BigDecimal> rates = ratesResponseDTO.getRates();
        Set<String> keySet = rates.keySet();
        return Mono.just(keySet);
    }

    private Mono<Transaction> convertSavingTransaction(Map<String, BigDecimal> rates, RequestDTO requestDTO) {
        BigDecimal originToEUR = rates.get(requestDTO.getOriginCurrency());
        BigDecimal euroValue = requestDTO.getValue().divide(originToEUR, RoundingMode.HALF_UP);
        BigDecimal eurToFinalCurrency = rates.get(requestDTO.getFinalCurrency());
        BigDecimal finalValue = euroValue.multiply(eurToFinalCurrency);
        BigDecimal conversionRate = finalValue.divide(requestDTO.getValue(), RoundingMode.HALF_UP);

        var transaction = Transaction.builder()
                .conversionRate(conversionRate)
                .finalCurrency(requestDTO.getFinalCurrency())
                .originValue(requestDTO.getValue())
                .finalValue(finalValue)
                .originCurrency(requestDTO.getOriginCurrency())
                .userId(requestDTO.getUserId())
                .createdAt(new Date())
                .build();

        return transactionRepository.save(transaction);
    }


}
