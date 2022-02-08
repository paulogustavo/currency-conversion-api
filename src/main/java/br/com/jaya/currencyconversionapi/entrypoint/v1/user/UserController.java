package br.com.jaya.currencyconversionapi.entrypoint.v1.user;

import br.com.jaya.currencyconversionapi.application.user.dto.UserDto;
import br.com.jaya.currencyconversionapi.application.user.service.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserApplicationService userApplicationService;

    @Autowired
    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping
    @Operation(summary = "Fetch all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public Flux<UserDto> getAll() {
        return userApplicationService.fetchUsers();
    }

}
