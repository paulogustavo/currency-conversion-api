package br.com.jaya.currencyconversionapi.repository;

import br.com.jaya.currencyconversionapi.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
}
