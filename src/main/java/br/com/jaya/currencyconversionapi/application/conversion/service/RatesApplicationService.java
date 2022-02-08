package br.com.jaya.currencyconversionapi.application.conversion.service;

import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.RatesMapper;
import br.com.jaya.currencyconversionapi.domain.conversion.service.RatesService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.enterprise.context.ApplicationScoped;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatesApplicationService {

    RatesService ratesService;
    RatesMapper ratesMapper;

    public Mono<RatesResponseDto> fetchRates(){
        return ratesService.fetchRates().map(ratesMapper::map);
    }
}
