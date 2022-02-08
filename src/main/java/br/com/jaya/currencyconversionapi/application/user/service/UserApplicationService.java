package br.com.jaya.currencyconversionapi.application.user.service;

import br.com.jaya.currencyconversionapi.application.user.dto.UserDto;
import br.com.jaya.currencyconversionapi.application.user.mapper.UserMapper;
import br.com.jaya.currencyconversionapi.domain.user.service.UserService;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Service
public class UserApplicationService {

    private UserService userService;
    private UserMapper userMapper;

    public Flux<UserDto> fetchUsers(){
        return userService.fetchUsers().map(user -> userMapper.map(user));
    }
}
