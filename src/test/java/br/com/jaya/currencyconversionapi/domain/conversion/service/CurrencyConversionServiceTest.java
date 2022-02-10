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
        var user = User.builder()
                .id("loremipsum123")
                .name("Paulo")
                .build();

        var conversionRequest = ConversionRequest.builder()
                .originCurrency(Currency.CAD)
                .finalCurrency(Currency.BRL)
                .value(BigDecimal.TEN)
                .userId(user.getId())
                .build();

        var expectedTransaction = getExpectedTransaction(new Date());

        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
        when(ratesRepository.fetchRates()).thenReturn(Mono.just(getRatesResponse()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(Mono.just(expectedTransaction));

        //Act
        var outputTransaction = currencyConversionService.convert(conversionRequest).block();

        //Verify
        assertThat(outputTransaction)
                .isEqualTo(expectedTransaction);

    }

    @Test
    void convert_ThrowsUserNotFoundException(){
        //Assemble
        var conversionRequest = ConversionRequest.builder()
                .originCurrency(Currency.CAD)
                .finalCurrency(Currency.BRL)
                .value(BigDecimal.TEN)
                .userId("loremipsum123")
                .build();

        when(userRepository.findById(anyString())).thenThrow(UserNotFoundException.class);

        //Act
        var throwable = catchThrowable(() -> currencyConversionService.convert(conversionRequest).block());

        //Verify
        assertThat(throwable)
                .isInstanceOf(UserNotFoundException.class);


    }

    private RatesResponse getRatesResponse(){
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("AUD", new BigDecimal("1.592647"));
        rates.put("MXN", new BigDecimal("23.493212"));
        rates.put("JPY", new BigDecimal("132.025941"));
        rates.put("PLN", new BigDecimal("4.512007"));
        rates.put("EUR", new BigDecimal("1"));
        rates.put("USD", new BigDecimal("1.14325"));
        rates.put("CAD", new BigDecimal("1.451373"));
        rates.put("BRL", new BigDecimal("6.022066"));

        var ratesResponse = new RatesResponse();
        ratesResponse.setRates(rates);

        return ratesResponse;
    }

    private Transaction getExpectedTransaction(Date createdAt){
        return Transaction.builder()
                .conversionRate(BigDecimal.ONE)
                .originCurrency(Currency.CAD.getDescription())
                .finalCurrency(Currency.BRL.getDescription())
                .createdAt(createdAt)
                .id("asdfad2342")
                .originValue(BigDecimal.TEN)
                .finalValue(BigDecimal.TEN)
                .userId("loremipsum")
                .build();
    }



}
