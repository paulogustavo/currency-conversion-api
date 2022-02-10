package br.com.jaya.currencyconversionapi.domain.conversion.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import reactor.core.publisher.Mono;

public interface RatesRepository {

    /**
     * Fetch rates - Domain repository interface
     * @return Mono<RatesResponseDto>
     */
    Mono<RatesResponse> fetchRates();

}
