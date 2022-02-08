package br.com.jaya.currencyconversionapi.domain.user.repository;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<User> fetchUsers();
    Mono<User> findById(String userId);
}
