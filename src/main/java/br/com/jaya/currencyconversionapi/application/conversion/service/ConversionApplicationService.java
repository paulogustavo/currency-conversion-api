package br.com.jaya.currencyconversionapi.application.conversion.service;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.ConversionMapper;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.TransactionMapper;
import br.com.jaya.currencyconversionapi.domain.conversion.service.CurrencyConversionService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Service
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversionApplicationService {

    CurrencyConversionService currencyConversionService;
    ConversionMapper conversionMapper;
    TransactionMapper transactionMapper;
    Validator validator;

    @Autowired
    public ConversionApplicationService(CurrencyConversionService currencyConversionService,
                                        ConversionMapper conversionMapper,
                                        TransactionMapper transactionMapper,
                                        Validator validator){
        this.currencyConversionService = currencyConversionService;
        this.conversionMapper = conversionMapper;
        this.transactionMapper = transactionMapper;
        this.validator = validator;
    }

    /**
     * Convert currency - Application layer service
     * @param conversionRequestDto Dto with conversion needed info
     * @return Mono<TransactionResponseDto> object with saved transaction
     */
    public Mono<TransactionResponseDto> convert(ConversionRequestDto conversionRequestDto){
        final var validationResults = validator.validate(conversionRequestDto);
        if(!validationResults.isEmpty())
            return Mono.error(new ConstraintViolationException(validationResults));
        return currencyConversionService.convert(conversionMapper.map(conversionRequestDto)).map(transactionMapper::map);
    }
}
