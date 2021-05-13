package br.com.jaya.currencyconversionapi.controller;

import br.com.jaya.currencyconversionapi.model.dto.RateDTO;
import br.com.jaya.currencyconversionapi.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/conversion")
public class ConversionController {

    private final CurrencyConversionService service;

    @Autowired
    public ConversionController(CurrencyConversionService service) {
        this.service = service;
    }

    @GetMapping
    Flux<RateDTO> getRates() {
        return service.findAll();
    }

}
