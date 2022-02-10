package br.com.jaya.currencyconversionapi.application.conversion.mapper;

import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionResponseDto;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Maps a Transaction object to a TransactionResponseDto object
     * @return TransactionResponseDto application object
     */
    TransactionResponseDto map(Transaction toMap);

}
