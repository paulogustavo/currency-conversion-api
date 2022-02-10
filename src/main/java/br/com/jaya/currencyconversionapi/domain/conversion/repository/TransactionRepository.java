package br.com.jaya.currencyconversionapi.domain.conversion.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {

    /**
     * Find all transactions
     * @return Flux<Transaction> object with users' transactions
     */
    Flux<Transaction> fetchTransactions(String userId);

    /**
     * Save transaction
     * @return Mono<Transaction> object with saved transaction
     */
    Mono<Transaction> save(Transaction transaction);

}
