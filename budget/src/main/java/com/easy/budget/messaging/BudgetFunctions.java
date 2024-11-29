package com.easy.budget.messaging;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.easy.budget.dto.Expense;
import com.easy.budget.service.BudgetService;

import reactor.core.publisher.Flux;


@Configuration
public class BudgetFunctions {

    private static final Logger log = LoggerFactory.getLogger(BudgetFunctions.class);

    @Bean
    Consumer<Flux<Expense>> budgetCreate(BudgetService budgetService) {
        return budgetDtoFlux -> budgetDtoFlux
                .doOnNext(budgetService::addBudget)
                .doOnNext(budgetDto -> log.info("Received new budget: {}", budgetDto))
                .subscribe();
    }
}
