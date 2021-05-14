package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.exception.CurrencyConversionException;
import br.com.jaya.currencyconversionapi.domain.Transaction;
import br.com.jaya.currencyconversionapi.domain.dto.RatesResponseDTO;
import br.com.jaya.currencyconversionapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

@Service
public class CurrencyConversionService {

    private static final String BASE_URL = "http://api.exchangeratesapi.io/";
    private static final String ACCESS_KEY = "895cc6dc0e2066458ca38e7d7012cd73";
    private static final String SYMBOLS = "USD,AUD,CAD,PLN,MXN,BRL,EUR";

    private final TransactionRepository transactionRepository;

    public CurrencyConversionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    WebClient webClient = WebClient.create(BASE_URL);

    public Mono<RatesResponseDTO> findAll()
    {
        return webClient.get()
                .uri("/latest?access_key=" + ACCESS_KEY + "&symbols=" + SYMBOLS + "&format=1")
                .retrieve()
                .bodyToMono(RatesResponseDTO.class);
    }

    public Mono<Transaction> convert(String originCurrency, String finalCurrency, BigDecimal originValue){
        return findAll().flatMap(ratesResponseDTO -> {
            Map<String, BigDecimal> rates = ratesResponseDTO.getRates();
            Set<String> keySet = rates.keySet();
            if(keySet.stream().noneMatch(currency -> currency.equalsIgnoreCase(originCurrency))){
                return Mono.error(new CurrencyConversionException("Origin currency not found. Only " + SYMBOLS + " permitted."));
            }

            if(keySet.stream().noneMatch(currency -> currency.equalsIgnoreCase(finalCurrency))){
                return Mono.error(new CurrencyConversionException("Final currency not found. Only " + SYMBOLS + " permitted."));
            }

            BigDecimal originToEUR = rates.get(originCurrency);

            BigDecimal euroValue = originValue.divide(originToEUR, RoundingMode.HALF_UP);

            BigDecimal eurToFinalCurrency = rates.get(finalCurrency);

            BigDecimal finalValue = euroValue.multiply(eurToFinalCurrency);

            BigDecimal conversionRate = finalValue.divide(originValue, RoundingMode.HALF_UP);

            var transaction = Transaction.builder()
                    .conversionRate(conversionRate)
                    .finalCurrency(finalCurrency)
                    .originValue(originValue)
                    .finalValue(finalValue)
                    .originCurrency(originCurrency)
                    .createdAt(Calendar.getInstance())
                    .build();

            return transactionRepository.save(transaction);

        });


    }

}
