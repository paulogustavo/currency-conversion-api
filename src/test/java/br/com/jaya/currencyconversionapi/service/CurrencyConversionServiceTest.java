package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.domain.Transaction;
import br.com.jaya.currencyconversionapi.domain.User;
import br.com.jaya.currencyconversionapi.domain.dto.RatesResponseDTO;
import br.com.jaya.currencyconversionapi.domain.dto.RequestDTO;
import br.com.jaya.currencyconversionapi.exception.CurrencyConversionException;
import br.com.jaya.currencyconversionapi.repository.TransactionRepository;
import br.com.jaya.currencyconversionapi.repository.UserRepository;
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
    UserRepository userRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    RatesService ratesService;

    @InjectMocks
    CurrencyConversionService currencyConversionService;

    private RequestDTO requestDTO;
    private RatesResponseDTO fetchRatesResponseDTO;
    private User user;
    private Transaction transaction;

    @BeforeEach
    public void init(){
        requestDTO = new RequestDTO("USD", BigDecimal.TEN, "BRL", "609ecfbab66b6314c06af684");

        fetchRatesResponseDTO = new RatesResponseDTO();
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("BRL", new BigDecimal("6.403258"));
        rates.put("USD", new BigDecimal("1.21459"));
        rates.put("EUR", BigDecimal.ONE);
        rates.put("JPY", new BigDecimal("132.845829"));
        fetchRatesResponseDTO.setRates(rates);

        user = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        transaction = Transaction.builder()
                .conversionRate(new BigDecimal("5.4"))
                .finalCurrency("BRL")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("54.00"))
                .originCurrency("USD")
                .userId("609ecfbab66b6314c06af684")
                .createdAt(new Date())
                .build();
    }

    @Test
    void testSaveTransaction_Successful(){
        Mockito.doReturn(Mono.just(transaction)).when(transactionRepository).save(Mockito.any());
        Mockito.doReturn(Mono.just(user)).when(userRepository).findById((String) Mockito.any());
        Mockito.doReturn(Mono.just(fetchRatesResponseDTO)).when(ratesService).fetchRates();

        StepVerifier.create(currencyConversionService.convert(requestDTO.getOriginCurrency(),
                requestDTO.getFinalCurrency(), requestDTO.getValue(), requestDTO.getUserId()))
                .expectSubscription()
                .expectNext(transaction)
                .expectComplete()
                .verify();

       Mockito.verify(transactionRepository, times(1)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNonPositiveOriginValue_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(user)).when(userRepository).findById((String) Mockito.any());
        Mockito.doReturn(Mono.just(fetchRatesResponseDTO)).when(ratesService).fetchRates();

        requestDTO.setValue(BigDecimal.ZERO);

        StepVerifier.create(currencyConversionService.convert(requestDTO.getOriginCurrency(),
                requestDTO.getFinalCurrency(), requestDTO.getValue(), requestDTO.getUserId()))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("Value informed should be greater than zero")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNullOriginValue_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(user)).when(userRepository).findById((String) Mockito.any());
        Mockito.doReturn(Mono.just(fetchRatesResponseDTO)).when(ratesService).fetchRates();

        requestDTO.setValue(null);

        StepVerifier.create(currencyConversionService.convert(requestDTO.getOriginCurrency(),
                requestDTO.getFinalCurrency(), requestDTO.getValue(), requestDTO.getUserId()))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("Value informed should not be null")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNullUserId_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.just(fetchRatesResponseDTO)).when(ratesService).fetchRates();

        requestDTO.setUserId(null);

        StepVerifier.create(currencyConversionService.convert(requestDTO.getOriginCurrency(),
                requestDTO.getFinalCurrency(), requestDTO.getValue(), requestDTO.getUserId()))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("User id should not be null")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testTryConvertSendingNonExistingUserId_ShouldThrowCustomException(){
        Mockito.doReturn(Mono.empty()).when(userRepository).findById((String) Mockito.any());
        Mockito.doReturn(Mono.just(fetchRatesResponseDTO)).when(ratesService).fetchRates();

        StepVerifier.create(currencyConversionService.convert(requestDTO.getOriginCurrency(),
                requestDTO.getFinalCurrency(), requestDTO.getValue(), requestDTO.getUserId()))
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof CurrencyConversionException &&
                        throwable.getMessage().equals("User not found")
                ).verify();

        Mockito.verify(transactionRepository, times(0)).save(Mockito.any());
    }




}
