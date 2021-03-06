package br.com.jaya.currencyconversionapi.domain.conversion.service;

import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Currency;
import br.com.jaya.currencyconversionapi.domain.conversion.model.RatesResponse;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.RatesRepository;
import br.com.jaya.currencyconversionapi.domain.conversion.repository.TransactionRepository;
import br.com.jaya.currencyconversionapi.domain.user.exception.UserNotFoundException;
import br.com.jaya.currencyconversionapi.domain.user.model.User;
import br.com.jaya.currencyconversionapi.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CurrencyConversionServiceTest {

    @InjectMocks
    CurrencyConversionService currencyConversionService;

    @Mock
    UserRepository userRepository;
    @Mock
    RatesRepository ratesRepository;
    @Mock
    TransactionRepository transactionRepository;

    @Test
    void convert_Ok(){
        //Assemble
        User user = User.builder()
                .id("loremipsum123")
                .name("Paulo")
                .build();

        ConversionRequest conversionRequest = ConversionRequest.builder()
                .originCurrency(Currency.BRL)
                .finalCurrency(Currency.AUD)
                .value(BigDecimal.TEN)
                .userId(user.getId())
                .build();

        Transaction expectedTransaction = getExpectedTransaction(new Date());

        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
        when(ratesRepository.fetchRates()).thenReturn(Mono.just(getRatesResponse()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(Mono.just(expectedTransaction));

        //Act
        Transaction outputTransaction = currencyConversionService.convert(conversionRequest).block();

        //Verify
        assertThat(outputTransaction)
                .isEqualTo(expectedTransaction);

    }

    @Test
    void convert_ThrowsUserNotFoundException(){
        //Assemble
        ConversionRequest conversionRequest = ConversionRequest.builder()
                .originCurrency(Currency.BRL)
                .finalCurrency(Currency.AUD)
                .value(BigDecimal.TEN)
                .userId("loremipsum123")
                .build();

        when(userRepository.findById(anyString())).thenThrow(UserNotFoundException.class);

        //Act
        Throwable throwable = catchThrowable(() -> currencyConversionService.convert(conversionRequest).block());

        //Verify
        assertThat(throwable)
                .isInstanceOf(UserNotFoundException.class);


    }

    private RatesResponse getRatesResponse(){
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("AUD", new BigDecimal("1.593275"));
        rates.put("MXN", new BigDecimal("23.492258"));
        rates.put("JPY", new BigDecimal("132.840936"));
        rates.put("PLN", new BigDecimal("4.50034"));
        rates.put("EUR", new BigDecimal("1"));
        rates.put("USD", new BigDecimal("1.146653"));
        rates.put("CAD", new BigDecimal("1.454816"));
        rates.put("BRL", new BigDecimal("6.00698"));

        RatesResponse ratesResponse = new RatesResponse();
        ratesResponse.setRates(rates);

        return ratesResponse;
    }

    private Transaction getExpectedTransaction(Date createdAt){
        return Transaction.builder()
                .conversionRate(new BigDecimal("0.265237269075"))
                .originCurrency(Currency.BRL.getDescription())
                .finalCurrency(Currency.AUD.getDescription())
                .createdAt(createdAt)
                .id("transaction123")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("2.65237269075"))
                .userId("loremipsum123")
                .build();
    }



}
