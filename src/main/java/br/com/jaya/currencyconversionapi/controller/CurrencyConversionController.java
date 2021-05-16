package br.com.jaya.currencyconversionapi.controller;

import br.com.jaya.currencyconversionapi.domain.Transaction;
import br.com.jaya.currencyconversionapi.domain.dto.RatesResponseDTO;
import br.com.jaya.currencyconversionapi.domain.dto.RequestDTO;
import br.com.jaya.currencyconversionapi.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/conversion")
public class CurrencyConversionController {

    private final CurrencyConversionService service;

    @Autowired
    public CurrencyConversionController(CurrencyConversionService service) {
        this.service = service;
    }

    @GetMapping(value = "/rates")
    public Mono<RatesResponseDTO> getRates() {
        return service.findAll();
    }

    @PostMapping
    public Mono<Transaction> convert(@RequestBody RequestDTO requestDTO) {
        return service.convert(requestDTO.getOriginCurrency(),
                requestDTO.getFinalCurrency(),
                requestDTO.getValue(),
                requestDTO.getUserId());
    }

}
