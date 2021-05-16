package br.com.jaya.currencyconversionapi.controller;

import br.com.jaya.currencyconversionapi.domain.Transaction;
import br.com.jaya.currencyconversionapi.domain.User;
import br.com.jaya.currencyconversionapi.domain.dto.RatesResponseDTO;
import br.com.jaya.currencyconversionapi.domain.dto.RequestDTO;
import br.com.jaya.currencyconversionapi.exception.CurrencyConversionException;
import br.com.jaya.currencyconversionapi.repository.TransactionRepository;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
import br.com.jaya.currencyconversionapi.service.CurrencyConversionService;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.*;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CurrencyConversionController.class)
@Import(CurrencyConversionService.class)
@ActiveProfiles("test")
class CurrencyConversionControllerTest {

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CurrencyConversionService service;

    @Autowired
    private WebTestClient webClient;

    private Transaction transaction;
    private User user;
    private RequestDTO requestDTO;

    @BeforeEach
    public void init(){
        /*user = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        transaction = Transaction.builder()
                .conversionRate(new BigDecimal("5.4"))
                .finalCurrency("BRL")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("54.00"))
                .originCurrency("USD")
                .userId("609ecfbab66b6314c06af684")
                .createdAt(new Date())
                .build();*/
        requestDTO = new RequestDTO("USD",BigDecimal.TEN, "BRL", "609ecfbab66b6314c06af684");
    }

    @Test
    void testFetchRatesShouldReturnOk() {
        RatesResponseDTO responseDTO = new RatesResponseDTO();
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("BRL", BigDecimal.ONE);
        rates.put("USD", BigDecimal.TEN);
        responseDTO.setRates(rates);

        Mockito.when(service.findAll()).thenReturn(Mono.just(responseDTO));

        webClient.get()
                .uri("/conversion/rates")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(service, times(1)).findAll();
    }

    @Test
    void testFetchRatesShouldThrowCustomException() {
       Mockito.when(service.findAll()).thenReturn(Mono.error(new CurrencyConversionException("Failed to fetch rates")));

        webClient.get()
                .uri("/conversion/rates")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody();

        Mockito.verify(service, times(1)).findAll();
    }

    @Test
    void testSaveTransactionWithNonPositiveOriginValueShouldThrowCustomException() {
        requestDTO.setValue(BigDecimal.ZERO);
        Mockito.when(service.convert(requestDTO.getOriginCurrency(), requestDTO.getFinalCurrency(), requestDTO.getValue(), requestDTO.getUserId()))
                .thenReturn(Mono.error(new CurrencyConversionException("Value informed should be greater than zero")));

        webClient.post()
                .uri("/conversion")
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody();

        Mockito.verify(service, times(0)).findAll();
    }

    //TransactionOk
    /*@Test
    void testSaveTransactionShouldReturnOk() {

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
    }*/

}
