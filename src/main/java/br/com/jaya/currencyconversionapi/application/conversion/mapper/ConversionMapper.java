package br.com.jaya.currencyconversionapi.application.conversion.mapper;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.CurrencyDto;
import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversionMapper {

    /**
     * Maps a ConversionRequestDto object to a ConversionRequest object
     * @return ConversionRequest domain object
     */
    ConversionRequest map(ConversionRequestDto toMap);

    /**
     * Maps a CurrencyDto object to a Currency object
     * @return Currency domain object
     */
    Currency map(CurrencyDto toMap);
}
