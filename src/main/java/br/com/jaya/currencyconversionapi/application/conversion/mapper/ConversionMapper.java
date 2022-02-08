package br.com.jaya.currencyconversionapi.application.conversion.mapper;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.CurrencyDto;
import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversionMapper {

    ConversionRequest map(ConversionRequestDto toMap);

    Currency map(CurrencyDto toMap);
}
