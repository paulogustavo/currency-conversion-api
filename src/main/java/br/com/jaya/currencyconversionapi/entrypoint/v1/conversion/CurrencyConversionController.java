package br.com.jaya.currencyconversionapi.entrypoint.v1.conversion;

import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionDto;
import br.com.jaya.currencyconversionapi.application.conversion.service.ConversionApplicationService;
import br.com.jaya.currencyconversionapi.application.conversion.service.RatesApplicationService;
import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import br.com.jaya.currencyconversionapi.application.conversion.service.TransactionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/currency-conversion")
public class CurrencyConversionController {

    private final ConversionApplicationService currencyConversionService;
    private final RatesApplicationService ratesApplicationService;
    private final TransactionApplicationService transactionApplicationService;

    @Autowired
    public CurrencyConversionController(ConversionApplicationService conversionApplicationService,
                                        RatesApplicationService ratesRepository,
                                        TransactionApplicationService transactionApplicationService) {
        this.currencyConversionService = conversionApplicationService;
        this.ratesApplicationService = ratesRepository;
        this.transactionApplicationService = transactionApplicationService;
    }

    @GetMapping(value = "/rates")
    @Operation(summary = "Fetch currency conversion rates")
    @ApiResponse(responseCode = "200", description = "Currency conversion rates list")
    public Mono<RatesResponseDto> getRates() {
        return ratesApplicationService.fetchRates();
    }

    @PostMapping(value = "convert")
    @Operation(summary = "Executes conversion and then saves related transaction")
    @ApiResponse(responseCode = "200", description = "Currency conversion successful")
    public Mono<TransactionDto> convert(@RequestBody ConversionRequestDto conversionRequestDto) {
        return currencyConversionService.convert(conversionRequestDto);
    }

    @GetMapping(value = "/user/{userId}/transactions")
    @Operation(summary = "Fetch transactions by user id")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    public Flux<TransactionDto> getTransactions(@PathVariable String userId) {
        return transactionApplicationService.fetchTransactions(userId);
    }

}
