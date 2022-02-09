package br.com.jaya.currencyconversionapi.infrastructure.data.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

public interface MongoTransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findAllByUserId(String userId);
}
