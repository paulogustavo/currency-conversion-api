package br.com.jaya.currencyconversionapi.infrastructure.repository;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import br.com.jaya.currencyconversionapi.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRepositoryImpl implements UserRepository {

    MongoUserRepository mongoUserRepository;

    @Override
    public Flux<User> fetchUsers() {
        return mongoUserRepository.findAll();
    }

    @Override
    public Mono<User> findById(String userId) {
        return mongoUserRepository.findById(userId);
    }
}
