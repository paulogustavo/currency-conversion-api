package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.domain.dto.RatesResponseDTO;
import br.com.jaya.currencyconversionapi.exception.CurrencyConversionException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RatesService {

    public static final String BASE_URL = "http://api.exchangeratesapi.io/";
    public static final String ACCESS_KEY = "895cc6dc0e2066458ca38e7d7012cd73";
    public static final String SYMBOLS = "USD,AUD,CAD,PLN,MXN,BRL,EUR,JPY";

    WebClient webClient = WebClient.create(BASE_URL);

    public Mono<RatesResponseDTO> fetchRates() {
        return webClient.get()
                .uri("/latest?access_key=" + ACCESS_KEY + "&symbols=" + SYMBOLS + "&format=1")
                .retrieve()
                .bodyToMono(RatesResponseDTO.class)
                .onErrorMap(ex -> new CurrencyConversionException("Failed to fetch rates."));
    }

}
