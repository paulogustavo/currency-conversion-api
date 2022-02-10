package br.com.jaya.currencyconversionapi.domain.user.repository;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    /**
     * Find all users
     * @return Flux<User> object from database
     */
    Flux<User> fetchUsers();

    /**
     * Find user by id
     * @param userId String user id
     * @return Mono<User> object
     */
    Mono<User> findById(String userId);
}
