package br.com.jaya.currencyconversionapi.application.user.service;

import br.com.jaya.currencyconversionapi.application.user.dto.UserResponseDto;
import br.com.jaya.currencyconversionapi.application.user.mapper.UserMapper;
import br.com.jaya.currencyconversionapi.domain.user.service.UserService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserApplicationService {

    UserService userService;
    UserMapper userMapper;

    /**
     * Find all users
     * @return Flux<User> object with users information
     */
    public Flux<UserResponseDto> fetchUsers(){
        return userService.fetchUsers().map(userMapper::map);
    }
}
