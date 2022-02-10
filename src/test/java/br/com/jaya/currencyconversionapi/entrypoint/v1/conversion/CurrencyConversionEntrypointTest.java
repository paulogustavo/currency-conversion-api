package br.com.jaya.currencyconversionapi.entrypoint.v1.conversion;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.CurrencyDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.service.ConversionApplicationService;
import br.com.jaya.currencyconversionapi.application.conversion.service.RatesApplicationService;
import br.com.jaya.currencyconversionapi.application.conversion.service.TransactionApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CurrencyConversionEntrypoint.class)
@ActiveProfiles("test")
class CurrencyConversionEntrypointTest {

    @MockBean
    ConversionApplicationService conversionApplicationService;

    @MockBean
    RatesApplicationService ratesApplicationService;

    @MockBean
    TransactionApplicationService transactionApplicationService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void convert_Ok() {
        //Assemble
        ConversionRequestDto conversionRequestDto = ConversionRequestDto.builder()
                .originCurrency(CurrencyDto.BRL)
                .finalCurrency(CurrencyDto.AUD)
                .value(BigDecimal.TEN)
                .userId("loremipsum123")
                .build();

        Date transactionCreateAt = new Date();
        TransactionResponseDto transactionResponseDto = getExpectedTransactionDto(transactionCreateAt);

        when(conversionApplicationService.convert(any(ConversionRequestDto.class))).thenReturn(Mono.just(transactionResponseDto));

        //Act and Assert
        webClient.post()
                .uri("/currency-conversion/convert")
                .bodyValue(conversionRequestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody();

    }

    @Test
    void convert_BadRequest() {
        //Assemble
        ConversionRequestDto conversionRequestDto = ConversionRequestDto.builder()
                .originCurrency(CurrencyDto.BRL)
                .finalCurrency(CurrencyDto.AUD)
                .value(BigDecimal.TEN)
                //.userId("loremipsum123")
                .build();

        when(conversionApplicationService.convert(any(ConversionRequestDto.class)))
                .thenThrow(ConstraintViolationException.class);

        //Act and Assert
        webClient.post()
                .uri("/currency-conversion/convert")
                .bodyValue(conversionRequestDto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody();

    }

    @Test
    void fetchRates_Ok() {
        //Assemble
        RatesResponseDto ratesResponseDto = getRatesResponseDto();

        when(ratesApplicationService.fetchRates()).thenReturn(Mono.just(ratesResponseDto));

        //Act and Assert
        webClient.get()
                .uri("/currency-conversion/rates")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

    }

    @Test
    void fetchTransactions_Ok() {
        //Assemble
        String userId = "loremipsum123";

        Date transactionCreateAt = new Date();
        TransactionResponseDto transactionResponseDto = getExpectedTransactionDto(transactionCreateAt);

        when(transactionApplicationService.fetchTransactions(any(String.class))).thenReturn(Flux.just(transactionResponseDto));

        //Act and Assert
        webClient.get()
                .uri("/currency-conversion/user/{userId}/transactions", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody();

    }

    private TransactionResponseDto getExpectedTransactionDto(Date createdAt){
        return TransactionResponseDto.builder()
                .conversionRate(new BigDecimal("0.265237269075"))
                .originCurrency(CurrencyDto.BRL.getDescription())
                .finalCurrency(CurrencyDto.AUD.getDescription())
                .createdAt(createdAt)
                .id("transaction123")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("2.65237269075"))
                .userId("loremipsum123")
                .build();
    }

    private RatesResponseDto getRatesResponseDto(){
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("AUD", new BigDecimal("1.593275"));
        rates.put("MXN", new BigDecimal("23.492258"));
        rates.put("JPY", new BigDecimal("132.840936"));
        rates.put("PLN", new BigDecimal("4.50034"));
        rates.put("EUR", new BigDecimal("1"));
        rates.put("USD", new BigDecimal("1.146653"));
        rates.put("CAD", new BigDecimal("1.454816"));
        rates.put("BRL", new BigDecimal("6.00698"));

        RatesResponseDto ratesResponse = new RatesResponseDto();
        ratesResponse.setRates(rates);

        return ratesResponse;
    }

}
