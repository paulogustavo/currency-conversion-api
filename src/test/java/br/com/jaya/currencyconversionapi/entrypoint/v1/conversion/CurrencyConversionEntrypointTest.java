package br.com.jaya.currencyconversionapi.entrypoint.v1.conversion;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.CurrencyDto;
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
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.Date;

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
        var conversionRequestDto = ConversionRequestDto.builder()
                .originCurrency(CurrencyDto.BRL)
                .finalCurrency(CurrencyDto.AUD)
                .value(BigDecimal.TEN)
                .userId("loremipsum123")
                .build();

        var transactionCreateAt = new Date();
        var transactionResponseDto = getExpectedTransactionDto(transactionCreateAt);

        when(conversionApplicationService.convert(any(ConversionRequestDto.class))).thenReturn(Mono.just(transactionResponseDto));

        //Act and Assert
        webClient.post()
                .uri("/currency-conversion/convert")
                .bodyValue(conversionRequestDto)
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

}
