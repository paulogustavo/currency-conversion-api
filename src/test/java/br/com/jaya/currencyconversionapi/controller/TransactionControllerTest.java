package br.com.jaya.currencyconversionapi.controller;

import br.com.jaya.currencyconversionapi.domain.Transaction;
import br.com.jaya.currencyconversionapi.domain.User;
import br.com.jaya.currencyconversionapi.repository.TransactionRepository;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
import br.com.jaya.currencyconversionapi.service.TransactionService;
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
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TransactionController.class)
@Import(TransactionService.class)
@ActiveProfiles("test")
class TransactionControllerTest {

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testGetRatesShouldReturnOk() {
        var user = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        var transaction = Transaction.builder()
                .conversionRate(new BigDecimal("5.4"))
                .finalCurrency("BRL")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("54.00"))
                .originCurrency("USD")
                .userId("609ecfbab66b6314c06af684")
                .createdAt(new Date())
                .build();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Mockito.when(transactionRepository.findAllByUserId("609ecfbab66b6314c06af684")).thenReturn(Flux.fromIterable(transactions));
        Mockito.when(userRepository.findById("609ecfbab66b6314c06af684")).thenReturn(Mono.just(user));

        webClient.get()
                .uri("/transactions/609ecfbab66b6314c06af684")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(transactionRepository, times(1)).findAllByUserId("609ecfbab66b6314c06af684");
    }

}
