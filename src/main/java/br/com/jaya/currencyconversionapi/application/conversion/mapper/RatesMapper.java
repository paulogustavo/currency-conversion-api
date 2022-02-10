package br.com.jaya.currencyconversionapi.application.conversion.mapper;

import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatesMapper {

    /**
     * Maps a RatesResponse object to a RatesResponseDto object
     * @return RatesResponseDto application object
     */
    RatesResponseDto map(RatesResponse toMap);

}
