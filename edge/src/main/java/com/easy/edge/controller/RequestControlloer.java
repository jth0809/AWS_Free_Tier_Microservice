package com.easy.edge.controller;

import com.easy.edge.dto.Alldto;
import com.easy.edge.service.RequestService;

import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class RequestControlloer {
    
    RequestService requestService;
    
    @PostMapping("/posts")
    public Mono<Void> orchestration(@RequestBody Alldto alldto) {
        requestService.publishEvent(alldto);
        return Mono.empty();
    }
    
}
