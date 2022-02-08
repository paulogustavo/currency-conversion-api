package br.com.jaya.currencyconversionapi.application.conversion.service;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionDto;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.ConversionMapper;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.TransactionMapper;
import br.com.jaya.currencyconversionapi.domain.conversion.service.CurrencyConversionService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.enterprise.context.ApplicationScoped;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversionApplicationService {

    CurrencyConversionService currencyConversionService;
    ConversionMapper conversionMapper;
    TransactionMapper transactionMapper;

    public Mono<TransactionDto> convert(ConversionRequestDto conversionRequestDto){
        return currencyConversionService.convert(conversionMapper.map(conversionRequestDto)).map(transactionMapper::map);
    }
}
