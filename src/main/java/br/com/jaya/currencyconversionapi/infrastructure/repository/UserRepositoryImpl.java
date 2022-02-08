package br.com.jaya.currencyconversionapi.infrastructure.repository;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import br.com.jaya.currencyconversionapi.domain.user.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserRepositoryImpl implements UserRepository {

    private MongoUserRepository mongoUserRepository;

    @Override
    public Flux<User> fetchUsers() {
        return mongoUserRepository.findAll();
    }

    @Override
    public Mono<User> findById(String userId) {
        return mongoUserRepository.findById(userId);
    }
}
