package com.easy.messaging;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easy.location.dto.Plandto;
import com.easy.location.service.TravelPlanService;

import reactor.core.publisher.Flux;


@Configuration
public class PlanFunctions {
    private static final Logger log = LoggerFactory.getLogger(PlanFunctions.class);
    private TravelPlanService planService;
    
    @Bean
    public Consumer<Flux<Plandto>> planCreate() {
        return planDtoFlux -> planDtoFlux
                .doOnNext(planService::addTravelPlan)
                .doOnNext(planDto -> log.info("Received new plan: {}", planDto))
                .subscribe();
    }
}
