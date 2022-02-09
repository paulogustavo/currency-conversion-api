package br.com.jaya.currencyconversionapi.infrastructure.data.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.RatesRepository;
import br.com.jaya.currencyconversionapi.infrastructure.data.exception.InfrastructureException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatesRepositoryImpl implements RatesRepository {

    @Value("${rates.url}")
    String url;

    public Mono<RatesResponse> fetchRates() {
        var ratesWebClient = WebClient.create(url);
        return ratesWebClient.get()
                .retrieve()
                .bodyToMono(RatesResponse.class)
                .onErrorMap(ex -> {
                    ex.printStackTrace();
                    return new InfrastructureException("Failed to fetch rates.");
                });
    }

}
