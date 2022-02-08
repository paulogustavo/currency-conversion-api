package br.com.jaya.currencyconversionapi.domain.conversion.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConversionRepository {
    Mono<Transaction> convert(ConversionRequest conversionRequest);
}
