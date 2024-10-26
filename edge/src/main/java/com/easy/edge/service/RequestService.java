package com.easy.edge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easy.edge.dto.Alldto;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;


@Service
public class RequestService {
    private final StreamBridge streamBridge;

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    public RequestService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishEvent(Alldto alldto) {
        log.info("Publishing orchestration event: {}", alldto);
        streamBridge.send("orchestration-out-0",  alldto);
        log.info("orchestration event published: {}", alldto);
    }
}
