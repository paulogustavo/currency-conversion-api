package br.com.jaya.currencyconversionapi;

import br.com.jaya.currencyconversionapi.domain.user.model.User;
import br.com.jaya.currencyconversionapi.infrastructure.repository.MongoUserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@SpringBootApplication
public class CurrencyConversionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionApiApplication.class, args);
	}

	@Bean
	@Profile("!test")
	@Transactional
	public ApplicationRunner criandoUsuarios(MongoUserRepository userRepository) {
		return args -> {
			Stream<User> stream = Stream.of(User.builder().name("Paulo").build(),
					User.builder().name("George").build(),
					User.builder().name("Raul").build(),
					User.builder().name("Emanuel").build());

			userRepository.saveAll(Flux.fromStream(stream)).subscribe();
		};
	}

}
