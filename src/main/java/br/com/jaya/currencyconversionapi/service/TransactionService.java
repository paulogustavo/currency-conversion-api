package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.model.Transaction;
import br.com.jaya.currencyconversionapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Flux<Transaction> all() {
        return this.transactionRepository.findAll();
    }

    public Mono<Transaction> get(String id) {
        return this.transactionRepository.findById(id);
    }
}
