package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import br.com.jaya.currencyconversionapi.infrastructure.repository.MongoUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @Mock
    MongoUserRepository userRepository;

    /*@InjectMocks
    UserService userService;*/

    /*@Test
    void testFindAllUsers_ShouldBeSuccessful(){
        var user1 = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        var user2 = User.builder().id("609ecfbab66b6314c06af685").name("George").build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Mockito.when(userRepository.findAll()).thenReturn(Flux.fromIterable(users));

        StepVerifier.create(userService.all())
                .expectSubscription()
                .expectNextCount(2)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findAll();

    }*/
}
