package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.domain.Transaction;
import br.com.jaya.currencyconversionapi.exception.CurrencyConversionException;
import br.com.jaya.currencyconversionapi.repository.TransactionRepository;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Flux<Transaction> getTransactionsByUserId(String userId) {
        if(userId == null || userId.isEmpty())
            return Flux.error(new CurrencyConversionException("User id not informed"));
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new CurrencyConversionException("User not found")))
                .flux()
                .flatMap(user -> this.transactionRepository.findAllByUserId(userId));

    }

}
