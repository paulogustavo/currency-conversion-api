package br.com.jaya.currencyconversionapi;

import br.com.jaya.currencyconversionapi.model.User;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
import br.com.jaya.currencyconversionapi.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@SpringBootApplication
public class CurrencyConversionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionApiApplication.class, args);
	}

	@Bean
	@Transactional
	ApplicationRunner criandoUsuarios(UserRepository userRepository, UserService service) {
		return args -> {
			Stream<User> stream = Stream.of(new User("Paulo"),
					new User("George"),
					new User("Raul"),
					new User("Emanuel"));

			userRepository.saveAll(Flux.fromStream(stream)).subscribe();
		};
	}

}
