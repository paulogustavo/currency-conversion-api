package br.com.jaya.currencyconversionapi.repository;

import br.com.jaya.currencyconversionapi.domain.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findAllByUserId(String userId);
}
