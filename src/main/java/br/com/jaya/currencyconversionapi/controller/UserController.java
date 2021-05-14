package br.com.jaya.currencyconversionapi.controller;

import br.com.jaya.currencyconversionapi.domain.User;
import br.com.jaya.currencyconversionapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    Flux<User> getAll() {
        return service.all();
    }

}
