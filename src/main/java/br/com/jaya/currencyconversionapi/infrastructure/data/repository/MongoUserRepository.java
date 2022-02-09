package br.com.jaya.currencyconversionapi.infrastructure.data.repository;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

public interface MongoUserRepository extends ReactiveMongoRepository<User, String> {
}
