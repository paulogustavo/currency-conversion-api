package br.com.jaya.currencyconversionapi.repository;

import br.com.jaya.currencyconversionapi.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
