package br.com.jaya.currencyconversionapi.domain.conversion.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import reactor.core.publisher.Mono;

public interface RatesRepository {
    Mono<RatesResponse> fetchRates();
}
