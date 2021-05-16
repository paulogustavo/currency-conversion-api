package br.com.jaya.currencyconversionapi.controller;

import br.com.jaya.currencyconversionapi.domain.Transaction;
import br.com.jaya.currencyconversionapi.domain.dto.RatesResponseDTO;
import br.com.jaya.currencyconversionapi.domain.dto.RequestDTO;
import br.com.jaya.currencyconversionapi.service.CurrencyConversionService;
import br.com.jaya.currencyconversionapi.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/conversion")
public class CurrencyConversionController {

    private final CurrencyConversionService currencyConversionService;
    private final RatesService ratesService;

    @Autowired
    public CurrencyConversionController(CurrencyConversionService currencyConversionService,
                                        RatesService ratesService) {
        this.currencyConversionService = currencyConversionService;
        this.ratesService = ratesService;
    }

    @GetMapping(value = "/rates")
    public Mono<RatesResponseDTO> getRates() {
        return ratesService.fetchRates();
    }

    @PostMapping
    public Mono<Transaction> convert(@RequestBody RequestDTO requestDTO) {
        return currencyConversionService.convert(requestDTO.getOriginCurrency(),
                requestDTO.getFinalCurrency(),
                requestDTO.getValue(),
                requestDTO.getUserId());
    }

}
