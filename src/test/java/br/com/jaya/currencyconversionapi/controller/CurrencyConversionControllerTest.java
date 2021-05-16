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

    private RequestDTO requestDTO;
    private RatesResponseDTO fetchRatesResponseDTO;

    @BeforeEach
    public void init(){
        requestDTO = new RequestDTO("USD",BigDecimal.TEN, "BRL", "609ecfbab66b6314c06af684");

        fetchRatesResponseDTO = new RatesResponseDTO();
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("BRL", new BigDecimal("6.403258"));
        rates.put("USD", new BigDecimal("1.21459"));
        rates.put("EUR", BigDecimal.ONE);
        rates.put("JPY", new BigDecimal("132.845829"));
        fetchRatesResponseDTO.setRates(rates);
    }

    @Test
    void testFetchRatesShouldReturnOk() {


        Mockito.when(service.fetchRates()).thenReturn(Mono.just(fetchRatesResponseDTO));

        webClient.get()
                .uri("/conversion/rates")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(service, times(1)).fetchRates();
    }

    @Test
    void testFetchRatesShouldThrowCustomException() {
       Mockito.when(service.fetchRates()).thenReturn(Mono.error(new CurrencyConversionException("Failed to fetch rates")));

        webClient.get()
                .uri("/conversion/rates")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody();

        Mockito.verify(service, times(1)).fetchRates();
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

        Mockito.verify(service, times(0)).fetchRates();
    }

    //TransactionOk
    @Test
    void testSaveTransactionShouldReturnOk() {

        User user = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        Transaction transaction = Transaction.builder()
                .conversionRate(new BigDecimal("5.4"))
                .finalCurrency("BRL")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("54.00"))
                .originCurrency("USD")
                .userId("609ecfbab66b6314c06af684")
                .createdAt(new Date())
                .build();

        Mockito.when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
        Mockito.when(userRepository.findById("609ecfbab66b6314c06af684")).thenReturn(Mono.just(user));
        Mockito.when(service.fetchRates()).thenReturn(Mono.just(fetchRatesResponseDTO));

        webClient.post()
                .uri("/conversion")
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody();

    }

}
