package br.com.jaya.currencyconversionapi.application.conversion.service;

import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.CurrencyDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.ConversionMapper;
import br.com.jaya.currencyconversionapi.application.conversion.mapper.TransactionMapper;
import br.com.jaya.currencyconversionapi.domain.conversion.model.ConversionRequest;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Currency;
import br.com.jaya.currencyconversionapi.domain.conversion.model.Transaction;
import br.com.jaya.currencyconversionapi.domain.conversion.service.CurrencyConversionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Date;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ConversionApplicationServiceTest {

    @InjectMocks
    ConversionApplicationService conversionApplicationService;

    @Mock
    CurrencyConversionService currencyConversionService;

    ConversionMapper conversionMapper = Mappers.getMapper(ConversionMapper.class);
    TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @BeforeEach
    void setUp(){
        conversionApplicationService.setValidator(validator);
        conversionApplicationService.setConversionMapper(conversionMapper);
        conversionApplicationService.setTransactionMapper(transactionMapper);
    }

    @Test
    void convert_Ok(){
        //Assemble
        var conversionDto = ConversionRequestDto.builder()
                .originCurrency(CurrencyDto.CAD)
                .finalCurrency(CurrencyDto.BRL)
                .value(BigDecimal.TEN)
                .userId("loremipsum123")
                .build();

        var createdAt = new Date();
        var expectedTransaction = getExpectedTransaction(createdAt);
        var expectedTransactionDto = getExpectedTransactionDto(createdAt);

        when(currencyConversionService.convert(any(ConversionRequest.class)))
                .thenReturn(Mono.just(expectedTransaction));

        //Act
        var outputTransaction = conversionApplicationService.convert(conversionDto).block();

        //Assert
        assertThat(expectedTransactionDto)
                .isEqualTo(outputTransaction);
    }

    @Test
    void convert_ThrowsConstraintValidationException(){
        //Assemble
        var conversionDto = ConversionRequestDto.builder()
                .originCurrency(CurrencyDto.CAD)
                .finalCurrency(CurrencyDto.BRL)
                .value(BigDecimal.TEN)
                //.userId("loremipsum123")
                .build();

        //Act
        var throwable = catchThrowable(() -> conversionApplicationService.convert(conversionDto).block());

        //Assert
        assertThat(throwable).isInstanceOf(ConstraintViolationException.class);
    }

    private TransactionResponseDto getExpectedTransactionDto(Date createdAt){
        return TransactionResponseDto.builder()
                .conversionRate(BigDecimal.ONE)
                .originCurrency(CurrencyDto.CAD.getDescription())
                .finalCurrency(CurrencyDto.BRL.getDescription())
                .createdAt(createdAt)
                .id("asdfad2342")
                .originValue(BigDecimal.TEN)
                .finalValue(BigDecimal.TEN)
                .userId("loremipsum")
                .build();
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
