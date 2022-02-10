package br.com.jaya.currencyconversionapi.domain.conversion.service;

import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.RatesRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatesService {

    RatesRepository ratesRepository;

    /**
     * Fetch rates - Domain layer service
     * @return Mono<RatesResponseDto>
     */
    public Mono<RatesResponse> fetchRates(){
        return ratesRepository.fetchRates();
    }
}
