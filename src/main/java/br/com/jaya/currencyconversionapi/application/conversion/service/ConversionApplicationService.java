package br.com.jaya.currencyconversionapi.application.conversion.service;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionDto;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.ConversionMapper;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.TransactionMapper;
import br.com.jaya.currencyconversionapi.domain.conversion.service.CurrencyConversionService;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@Service
public class ConversionApplicationService {

    private CurrencyConversionService currencyConversionService;
    private ConversionMapper conversionMapper;
    private TransactionMapper transactionMapper;


    public Mono<TransactionDto> convert(ConversionRequestDto conversionRequestDto){
        //TODO executar validação

        return currencyConversionService.convert(conversionMapper.map(conversionRequestDto)).map(transaction -> transactionMapper.map(transaction));
    }
}
