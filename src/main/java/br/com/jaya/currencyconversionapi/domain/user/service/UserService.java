package br.com.jaya.currencyconversionapi.domain.user.service;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import br.com.jaya.currencyconversionapi.domain.user.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Data
@Service
public class UserService {
    private UserRepository userRepository;
    public Flux<User> fetchUsers(){
        return userRepository.fetchUsers();
    }
}
