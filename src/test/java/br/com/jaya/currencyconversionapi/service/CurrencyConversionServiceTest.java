package br.com.jaya.currencyconversionapi.service;
/*
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import br.com.jaya.currencyconversionapi.domain.user.model.User;
import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.infrastructure.exception.CurrencyConversionException;
import br.com.jaya.currencyconversionapi.infrastructure.repository.RatesRepositoryImpl;
import br.com.jaya.currencyconversionapi.infrastructure.repository.MongoTransactionRepository;
import br.com.jaya.currencyconversionapi.infrastructure.repository.MongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CurrencyConversionServiceTest {

    @Mock
    MongoUserRepository userRepository;
    @Mock
    MongoTransactionRepository transactionRepository;
    @Mock
    RatesRepositoryImpl ratesService;

    @InjectMocks
    CurrencyConversionService currencyConversionService;

    private ConversionRequestDto conversionRequestDTO;
    private RatesResponseDto fetchRatesResponseDto;
    private User user;
    private Transaction transaction;

    @BeforeEach
    public void init(){
        conversionRequestDTO = new ConversionRequestDto("USD", BigDecimal.TEN, "BRL", "609ecfbab66b6314c06af684");

        fetchRatesResponseDto = new RatesResponseDto();
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("BRL", new BigDecimal("6.403258"));
        rates.put("USD", new BigDecimal("1.21459"));
        rates.put("EUR", BigDecimal.ONE);
        rates.put("JPY", new BigDecimal("132.845829"));
        fetchRatesResponseDto.setRates(rates);

        user = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        transaction = Transaction.builder()
                .conversionRate(new BigDecimal("5.27309783152"))
                .originCurrency("USD")
                .finalCurrency("BRL")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("52.730978315204"))
                .userId("609ecfbab66b6314c06af684")
                .createdAt(new Date())
                .build();
    }*/

    /*@Test
    void testSaveTransaction_Successful(){
        Mockito.doReturn(Mono.just(transaction)).when(transactionRepository).save(Mockito.any());
        Mockito.doReturn(Mono.just(user)).when(userRepository).findById((String) Mockito.any());
        Mockito.doReturn(Mono.just(fetchRatesResponseDto)).when(ratesService).fetchRates();

        StepVerifier.create(currencyConversionService.convert(conversionRequestDTO))
                .expectSubscription()
                .expectNext(transaction)
                .expectComplete()
                .verify();

       Mockito.verify(transactionRepository, times(1)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNonPositiveOriginValue_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(user)).when(userRepository).findById((String) Mockito.any());
        Mockito.doReturn(Mono.just(fetchRatesResponseDto)).when(ratesService).fetchRates();

        conversionRequestDTO.setValue(BigDecimal.ZERO);

        StepVerifier.create(currencyConversionService.convert(conversionRequestDTO))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("Value informed should be greater than zero")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNullOriginValue_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(user)).when(userRepository).findById((String) Mockito.any());
        Mockito.doReturn(Mono.just(fetchRatesResponseDto)).when(ratesService).fetchRates();

        conversionRequestDTO.setValue(null);

        StepVerifier.create(currencyConversionService.convert(conversionRequestDTO))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("Value informed should not be null")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNullUserId_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(fetchRatesResponseDto)).when(ratesService).fetchRates();

        conversionRequestDTO.setUserId(null);

        StepVerifier.create(currencyConversionService.convert(conversionRequestDTO))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("User id should not be null")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNonExistingUserId_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.empty()).when(userRepository).findById((String) Mockito.any());
        Mockito.doReturn(Mono.just(fetchRatesResponseDto)).when(ratesService).fetchRates();

        StepVerifier.create(currencyConversionService.convert(conversionRequestDTO))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("User not found")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingEqualCurrencies_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(fetchRatesResponseDto)).when(ratesService).fetchRates();
        conversionRequestDTO.setFinalCurrency("USD");

        StepVerifier.create(currencyConversionService.convert(conversionRequestDTO))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("Origin and final currencies should not be equal")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNullOriginCurrency_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(fetchRatesResponseDto)).when(ratesService).fetchRates();
        conversionRequestDTO.setOriginCurrency(null);

        StepVerifier.create(currencyConversionService.convert(conversionRequestDTO))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("Origin currency should not be null")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNullFinalCurrency_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(fetchRatesResponseDto)).when(ratesService).fetchRates();
        conversionRequestDTO.setFinalCurrency(null);

        StepVerifier.create(currencyConversionService.convert(conversionRequestDTO))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("Final currency should not be null")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }*/




//}
