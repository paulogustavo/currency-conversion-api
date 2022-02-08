package br.com.jaya.currencyconversionapi.application.conversion.service;

import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionDto;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.TransactionMapper;
import br.com.jaya.currencyconversionapi.domain.conversion.service.TransactionService;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Data
@Service
public class TransactionApplicationService {

    private TransactionService transactionService;
    private TransactionMapper transactionMapper;

    public Flux<TransactionDto> fetchTransactions(String userId){
        return transactionService.fetchTransactions(userId).map(transaction -> transactionMapper.map(transaction));
    }
}
