package br.com.jaya.currencyconversionapi.entrypoint.v1.user;

import br.com.jaya.currencyconversionapi.application.user.dto.UserResponseDto;
import br.com.jaya.currencyconversionapi.application.user.service.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserEntrypoint {

    private final UserApplicationService userApplicationService;

    @Autowired
    public UserEntrypoint(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping
    @Operation(summary = "Fetch all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public Flux<UserResponseDto> getUsers() {
        log.info("New users fetching request");
        return userApplicationService.fetchUsers()
                .map(userResponseDto -> {
                    log.info("User fetching successful: {}", userResponseDto);
                    return userResponseDto;
                });
    }

}
