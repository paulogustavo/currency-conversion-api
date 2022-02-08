package br.com.jaya.currencyconversionapi.infrastructure.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.TransactionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TransactionRepositoryImpl implements TransactionRepository {

    private MongoTransactionRepository mongoTransactionRepository;

    @Override
    public Flux<Transaction> fetchTransactions(String userId) {
        return mongoTransactionRepository.findAllByUserId(userId);
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return mongoTransactionRepository.save(transaction);
    }
}
