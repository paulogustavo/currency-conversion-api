package br.com.jaya.currencyconversionapi.application.conversion.mapper;

import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionDto;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto map(Transaction toMap);

}
