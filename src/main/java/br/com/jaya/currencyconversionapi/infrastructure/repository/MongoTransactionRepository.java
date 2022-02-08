package br.com.jaya.currencyconversionapi.infrastructure.repository;

import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MongoTransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findAllByUserId(String userId);
}
