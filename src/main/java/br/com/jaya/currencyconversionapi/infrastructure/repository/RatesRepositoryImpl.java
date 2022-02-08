package br.com.jaya.currencyconversionapi.infrastructure.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.RatesRepository;
import br.com.jaya.currencyconversionapi.infrastructure.exception.CurrencyConversionException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RatesRepositoryImpl implements RatesRepository {

    public static final String BASE_URL = "http://api.exchangeratesapi.io/";
    public static final String ACCESS_KEY = "895cc6dc0e2066458ca38e7d7012cd73";
    public static final String SYMBOLS = "USD,AUD,CAD,PLN,MXN,BRL,EUR,JPY";

    WebClient webClient = WebClient.create(BASE_URL);

    public Mono<RatesResponse> fetchRates() {
        return webClient.get()
                .uri("/latest?access_key=" + ACCESS_KEY + "&symbols=" + SYMBOLS + "&format=1")
                .retrieve()
                .bodyToMono(RatesResponse.class)
                .onErrorMap(ex -> new CurrencyConversionException("Failed to fetch rates."));
    }

}
