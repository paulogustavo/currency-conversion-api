package br.com.jaya.currencyconversionapi.controller;

import br.com.jaya.currencyconversionapi.domain.User;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
import br.com.jaya.currencyconversionapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@Import(UserService.class)
@ActiveProfiles("test")
class UserControllerTest {
    @MockBean
    UserRepository repository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testFindAllUsersShouldReturnOk() {
        var user1 = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        var user2 = User.builder().id("609ecfbab66b6314c06af685").name("George").build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Mockito.when(repository.findAll()).thenReturn(Flux.fromIterable(users));

        webClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(repository, times(1)).findAll();
    }
}
