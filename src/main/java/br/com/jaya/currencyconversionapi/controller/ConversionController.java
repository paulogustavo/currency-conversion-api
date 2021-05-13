package br.com.jaya.currencyconversionapi.controller;

import br.com.jaya.currencyconversionapi.model.Transaction;
import br.com.jaya.currencyconversionapi.model.dto.RatesResponseDTO;
import br.com.jaya.currencyconversionapi.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/conversion")
public class ConversionController {

    private final CurrencyConversionService service;

    @Autowired
    public ConversionController(CurrencyConversionService service) {
        this.service = service;
    }

    @GetMapping
    Mono<RatesResponseDTO> getRates() {
        return service.findAll();
    }

    @PostMapping(value = "/{originCurrency}/{value}/{finalCurrency}")
    Mono<Transaction> convert(@PathVariable String originCurrency,
                              @PathVariable BigDecimal value,
                              @PathVariable String finalCurrency) {
        return service.convert(originCurrency, finalCurrency, value);
    }

}
