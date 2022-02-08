package br.com.jaya.currencyconversionapi.application.user.service;

import br.com.jaya.currencyconversionapi.application.user.dto.UserDto;
import br.com.jaya.currencyconversionapi.application.user.mapper.UserMapper;
import br.com.jaya.currencyconversionapi.domain.user.service.UserService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserApplicationService {

    UserService userService;
    UserMapper userMapper;

    public Flux<UserDto> fetchUsers(){
        return userService.fetchUsers().map(userMapper::map);
    }
}
