package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.model.dto.RateDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class CurrencyConversionService {

    WebClient webClient = WebClient.create("http://api.exchangeratesapi.io/");

    public Flux<RateDTO> findAll()
    {
        return webClient.get()
                .uri("/latest?access_key=895cc6dc0e2066458ca38e7d7012cd73&symbols=USD,AUD,CAD,PLN,MXN,BRL&format=1")
                .retrieve()
                .bodyToFlux(RateDTO.class);
    }

}
