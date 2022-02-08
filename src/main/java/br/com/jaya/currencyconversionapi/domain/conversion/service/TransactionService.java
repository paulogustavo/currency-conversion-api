package br.com.jaya.currencyconversionapi.domain.conversion.service;

import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {

    TransactionRepository transactionRepository;

    public Flux<Transaction> fetchTransactions(String userId){
        return transactionRepository.fetchTransactions(userId);
    }
}
