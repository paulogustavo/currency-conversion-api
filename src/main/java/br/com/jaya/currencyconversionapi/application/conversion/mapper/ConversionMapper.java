package br.com.jaya.currencyconversionapi.application.conversion.mapper;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionDto;
import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversionMapper {

    ConversionRequest map(ConversionRequestDto toMap);

}
