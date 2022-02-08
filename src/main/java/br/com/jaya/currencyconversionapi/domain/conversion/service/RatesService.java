package br.com.jaya.currencyconversionapi.domain.conversion.service;

import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.RatesRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@Service
public class RatesService {
    private RatesRepository ratesRepository;
    public Mono<RatesResponse> fetchRates(){
        return ratesRepository.fetchRates();
    }
}
