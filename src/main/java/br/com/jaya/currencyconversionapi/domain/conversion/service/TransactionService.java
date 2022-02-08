package br.com.jaya.currencyconversionapi.domain.conversion.service;

import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.TransactionRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Data
@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    public Flux<Transaction> fetchTransactions(String userId){
        return transactionRepository.fetchTransactions(userId);
    }
}
