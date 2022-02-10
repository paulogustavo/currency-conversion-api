package br.com.jaya.currencyconversionapi.entrypoint.v1.user;

import br.com.jaya.currencyconversionapi.application.user.dto.UserResponseDto;
import br.com.jaya.currencyconversionapi.application.user.service.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserEntrypoint.class)
@ActiveProfiles("test")
class UserEntrypointTest {

    @MockBean
    UserApplicationService userApplicationService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void fetchUsers_Ok() {
        //Assemble
        UserResponseDto userResponseDto1 = UserResponseDto.builder()
                .id("lorem123")
                .name("Paulo")
                .build();

        UserResponseDto userResponseDto2 = UserResponseDto.builder()
                .id("ipsum123")
                .name("Gustavo")
                .build();

        List<UserResponseDto> users = new ArrayList<>();
        users.add(userResponseDto1);
        users.add(userResponseDto2);

        when(userApplicationService.fetchUsers()).thenReturn(Flux.fromIterable(users));

        //Act and Assert
        webClient.get()
                .uri("/users")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

    }

}
