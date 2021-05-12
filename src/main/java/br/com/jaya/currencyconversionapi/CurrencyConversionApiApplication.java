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
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class CurrencyConversionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionApiApplication.class, args);
	}

	@Bean
	@Transactional
	ApplicationRunner init(UserRepository userRepository, UserService service) {
		return args -> {
			/*Mono<User> userMono =
					service.create("Paulo Gustavo Aguiar");

			Mono<User> userMonoRetrieved = userMono.flatMap(saved -> service.get(saved.getId()));
			userMonoRetrieved.subscribe(u -> System.out.println(u.getName()));*/

			Stream<User> stream = Stream.of(new User("Paulo"),
					new User("George"),
					new User("Raul"),
					new User("Emanuel"));

			userRepository.saveAll(Flux.fromStream(stream)).subscribe();

			/*List<Transaction> transactions = transactionRepository.findAll();
			transactions.forEach(tra -> System.out.println(tra.getConversionRate()));*/

			System.out.println("Finished");

			/*Transaction transaction = Transaction.builder()
					.conversionRate(new BigDecimal("1"))
					.user(User.builder().id(5L).build())
					.convertedCurrency("USD")
					.convertedValue(BigDecimal.TEN)
					.createdAt(Calendar.getInstance())
					.originCurrency("BRL")
					.originValue(BigDecimal.ONE)
					.build();

			transactionRepository.save(transaction);*/


		};
	}
}
