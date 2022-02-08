package br.com.jaya.currencyconversionapi.entrypoint.v1.conversion;

import br.com.jaya.currencyconversionapi.application.conversion.dto.TransactionDto;
import br.com.jaya.currencyconversionapi.application.conversion.service.ConversionApplicationService;
import br.com.jaya.currencyconversionapi.application.conversion.service.RatesApplicationService;
import br.com.jaya.currencyconversionapi.application.conversion.dto.RatesResponseDto;
import br.com.jaya.currencyconversionapi.application.conversion.dto.ConversionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/conversion")
public class CurrencyConversionController {

    /*private final ConversionApplicationService currencyConversionService;
    private final RatesApplicationService ratesApplicationService;

    @Autowired
    public CurrencyConversionController(ConversionApplicationService conversionApplicationService,
                                        RatesApplicationService ratesRepository) {
        this.currencyConversionService = conversionApplicationService;
        this.ratesApplicationService = ratesRepository;
    }

    @GetMapping(value = "/rates")
    public Mono<RatesResponseDto> getRates() {
        return ratesApplicationService.fetchRates();
    }

    @PostMapping
    public Mono<TransactionDto> convert(@RequestBody ConversionRequestDto conversionRequestDto) {
        return currencyConversionService.convert(conversionRequestDto);
    }*/

}
