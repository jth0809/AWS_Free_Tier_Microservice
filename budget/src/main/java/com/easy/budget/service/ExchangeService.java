package com.easy.budget.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easy.budget.dto.ExchangeDto;
import com.easy.budget.domain.ExchangeRate;
import com.easy.budget.repository.ExchangeRepository;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExchangeService {
    ExchangeRepository exchangeRepository;
    
    public ExchangeService(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public List<ExchangeDto> getExchangeRates() {
        return exchangeRepository.findAll().stream()
                .map(exchangeRate -> new ExchangeDto(
                    exchangeRate.getCurrencyCode(), 
                    exchangeRate.getRate()
                ))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void addExchangeRate(ExchangeDto exchangeDto) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrencyCode(exchangeDto.currency());
        exchangeRate.setRate(exchangeDto.rate());
        exchangeRepository.save(exchangeRate);
    }
    @Transactional
    public void updateExchangeRate(ExchangeDto exchangeDto) {
        ExchangeRate exchangeRate = exchangeRepository.findById(exchangeDto.currency())
                .orElseThrow(() -> new NoSuchElementException("Exchange rate not found"));
        exchangeRate.setRate(exchangeDto.rate());
        exchangeRepository.save(exchangeRate);
    }
    @Transactional
    public void deleteExchangeRate(ExchangeDto exchangeDto) {
        exchangeRepository.deleteById(exchangeDto.currency());
    }
}
