package com.easy.location.dto;

import java.util.List;

import reactor.core.publisher.Mono;

public record ReactivePlanMyItemList(
    Mono<List<MyItemList>> myItemList,
    Mono<List<PlanList>> planList
){}
