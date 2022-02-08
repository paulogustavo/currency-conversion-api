package br.com.jaya.currencyconversionapi.application.conversion.service;

import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.RatesMapper;
import br.com.jaya.currencyconversionapi.domain.conversion.service.RatesService;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@Service
public class RatesApplicationService {

    private RatesService ratesService;
    private RatesMapper ratesMapper;

    public Mono<RatesResponseDto> fetchRates(){
        return ratesService.fetchRates().map(ratesResponse -> ratesMapper.map(ratesResponse));
    }
}
