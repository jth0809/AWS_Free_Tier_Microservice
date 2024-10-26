package com.transaction.orchestration.messaging;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.transaction.orchestration.dto.Alldto;
import com.transaction.orchestration.service.OrchestrationService;

import reactor.core.publisher.Flux;


@Configuration
public class TransactionFunctions {

    private static final Logger log = LoggerFactory.getLogger(TransactionFunctions.class);


    @Bean
    public Consumer<Flux<Alldto>> transactionPost(OrchestrationService orchestrationService) {
        return allDtoFlux -> allDtoFlux
                .doOnNext(allDto -> log.info("Received new transaction: {}", allDto))
                .doOnNext(orchestrationService::publishBudgetEvent)
                .doOnNext(orchestrationService::publishPlanEvent)
                .doOnNext(orchestrationService::publishPostEvent)
                .doOnNext(allDto -> log.info("All events published: {}", allDto))
                .subscribe();
    }
}
