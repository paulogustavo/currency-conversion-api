package br.com.jaya.currencyconversionapi.infrastructure.data.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.RatesRepository;
import br.com.jaya.currencyconversionapi.infrastructure.data.exception.InfrastructureException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatesRepositoryImpl implements RatesRepository {

    /**
     * external api url for fetching currency conversion rates
     */
    @Value("${rates.url}")
    String url;

    /**
     * Fetch rates from external api - Infrastructure layer
     * @return Mono<RatesResponseDto> object with conversion rates
     */
    public Mono<RatesResponse> fetchRates() {
        WebClient ratesWebClient = WebClient.create(url);
        return ratesWebClient.get()
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new InfrastructureException("Communication error with rates api client")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new InfrastructureException("Rates api client internal error")))
                .bodyToMono(RatesResponse.class)
                .onErrorMap(ex -> new InfrastructureException("Failed to fetch rates."));
    }

}
