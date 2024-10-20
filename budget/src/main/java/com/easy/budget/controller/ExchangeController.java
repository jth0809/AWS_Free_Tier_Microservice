package com.easy.budget.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.easy.budget.dto.ExchangeDto;
import com.easy.budget.service.ExchangeService;

@RestController
public class ExchangeController {

    ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/exchange")
    public List<ExchangeDto> getExchangeRate() {
        return exchangeService.getExchangeRates();
    }
    @PostMapping("/exchange")
    public void addExchangeRate(ExchangeDto exchangeDto) {
        exchangeService.addExchangeRate(exchangeDto);
    }
    @PutMapping("/exchange")
    public void updateExchangeRate(ExchangeDto exchangeDto) {
        exchangeService.updateExchangeRate(exchangeDto);
    }
    @DeleteMapping("/exchange")
    public void deleteExchangeRate(ExchangeDto exchangeDto) {
        exchangeService.deleteExchangeRate(exchangeDto);
    }
}
