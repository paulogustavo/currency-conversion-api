package br.com.jaya.currencyconversionapi.entrypoint.v1.conversion;

import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.service.ConversionApplicationService;
import br.com.jaya.currencyconversionapi.application.conversion.service.RatesApplicationService;
import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.service.TransactionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/currency-conversion")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyConversionEntrypoint {

    ConversionApplicationService conversionApplicationService;
    RatesApplicationService ratesApplicationService;
    TransactionApplicationService transactionApplicationService;

    @Autowired
    public CurrencyConversionEntrypoint(ConversionApplicationService conversionApplicationService,
                                        RatesApplicationService ratesRepository,
                                        TransactionApplicationService transactionApplicationService) {
        this.conversionApplicationService = conversionApplicationService;
        this.ratesApplicationService = ratesRepository;
        this.transactionApplicationService = transactionApplicationService;
    }

    @GetMapping(value = "/rates")
    @Operation(summary = "Fetch currency conversion rates (base EUR)")
    @ApiResponse(responseCode = "200", description = "Currency conversion rates list")
    public Mono<RatesResponseDto> getRates() {
        log.info("New rates fetching request");
        return ratesApplicationService.fetchRates()
                .map(ratesResponseDto -> {
                    log.info("Rates fetching successful: {}", ratesResponseDto);
                    return ratesResponseDto;
                });
    }

    @PostMapping(value = "convert")
    @Operation(summary = "Executes conversion and then saves related transaction")
    @ApiResponse(responseCode = "200", description = "Currency conversion successful")
    public Mono<TransactionResponseDto> convert(@RequestBody ConversionRequestDto conversionRequestDto) {
        log.info("New currency conversion request: {}", conversionRequestDto);
        return conversionApplicationService.convert(conversionRequestDto)
                .map(transactionDto -> {
                    log.info("Currency conversion successful: {}", transactionDto);
                    return transactionDto;
                });
    }

    @GetMapping(value = "/user/{userId}/transactions")
    @Operation(summary = "Fetch transactions by user id")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    public Flux<TransactionResponseDto> getTransactions(@PathVariable String userId) {
        log.info("New transactions fetching request");
        return transactionApplicationService.fetchTransactions(userId)
                .map(transactionDto -> {
                    log.info("Transaction fetching successful: {}", transactionDto);
                    return transactionDto;
                });
    }

}
