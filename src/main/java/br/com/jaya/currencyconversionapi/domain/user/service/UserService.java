package br.com.jaya.currencyconversionapi.domain.user.service;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import br.com.jaya.currencyconversionapi.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    public Flux<User> fetchUsers(){
        return userRepository.fetchUsers();
    }
}
