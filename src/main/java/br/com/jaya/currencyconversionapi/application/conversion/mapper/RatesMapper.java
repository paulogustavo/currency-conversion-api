package br.com.jaya.currencyconversionapi.application.conversion.mapper;

import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface RatesMapper {

    RatesResponseDto map(RatesResponse toMap);

}
