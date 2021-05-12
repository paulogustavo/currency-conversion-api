package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.model.User;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;

    UserService(ApplicationEventPublisher publisher, UserRepository userRepository) {
        this.publisher = publisher;
        this.userRepository = userRepository;
    }

    public Flux<User> all() {
        return this.userRepository.findAll();
    }

    public Mono<User> get(String id) {
        return this.userRepository.findById(id);
    }

    public Mono<User> create(String name) {
        return this.userRepository
                .save(new User(null, name))
                .doOnError(throwable -> {throw new RuntimeException("Error when creating user. Please try again.");});
    }
}
