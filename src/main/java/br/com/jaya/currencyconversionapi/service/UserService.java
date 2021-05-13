package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.model.User;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> all() {
        return this.userRepository.findAll();
    }

    public Mono<User> get(String id) {
        return this.userRepository.findById(id);
    }
}
